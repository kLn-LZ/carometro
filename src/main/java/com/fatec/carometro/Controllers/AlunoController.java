package com.fatec.carometro.Controllers;

import com.fatec.carometro.DTOs.AlunoDTO;
import com.fatec.carometro.Entities.Aluno;
import com.fatec.carometro.Entities.StatusValidacao;
import com.fatec.carometro.Services.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping("/registroAluno")
    public String mostraTelaRegistro(Model model) {
        AlunoDTO alunoDTO = new AlunoDTO(
                null, null, null, null, null, null, null, null, false, null,
                StatusValidacao.PENDENTE, null
        );
        model.addAttribute("alunoDTO", alunoDTO);
        return "registroAluno";
    }

    @PostMapping("/registroAluno")
    public String registraAluno(@ModelAttribute @Valid AlunoDTO alunoDTO,
                                BindingResult result,
                                Model model) throws IOException {
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(error ->
                    System.out.println("Erro no campo '" + error.getField() + "': " + error.getDefaultMessage())
            );
            model.addAttribute("error", "Por favor, corrija os erros no formulário.");
            return "registroAluno";
        }

        try {
            MultipartFile foto = alunoDTO.foto();
            if (foto == null || foto.isEmpty()) {
                model.addAttribute("error", "A foto é obrigatória.");
                return "registroAluno";
            }

            Aluno aluno = alunoDTO.toEntity();
            aluno.setStatusValidacao(StatusValidacao.PENDENTE); // resetar status
            aluno.setMotivoRejeicao(null); // limpar o motivo da rejeição

            alunoService.registraAluno(aluno);
            return "registroSucesso";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "registroAluno";
        }
    }

    @GetMapping("/validar-aluno/{id}")
    public String exibirValidacao(@PathVariable Long id, Model model) {
        Aluno aluno = alunoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        model.addAttribute("aluno", aluno);

        if (aluno.getFoto() != null && aluno.getFoto().length > 0) {
            String fotoBase64 = Base64.getEncoder().encodeToString(aluno.getFoto());
            model.addAttribute("fotoBase64", fotoBase64);
        }

        return "validar-aluno";
    }

    @PostMapping("/validar-aluno")
    public String validarAluno(@RequestParam Long id,
                               @RequestParam String statusValidacao,
                               @RequestParam(required = false) String motivoRejeicao) {

        Aluno aluno = alunoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        if ("APROVADO".equals(statusValidacao)) {
            aluno.setStatusValidacao(StatusValidacao.APROVADO);
            aluno.setMotivoRejeicao(null);
        } else {
            aluno.setStatusValidacao(StatusValidacao.REJEITADO);
            aluno.setMotivoRejeicao(motivoRejeicao);
        }

        alunoService.registraAluno(aluno);

        return "redirect:/validar-postagens";
    }

    @GetMapping("/validar-postagens")
    public String listarPostagensPendentes(Model model) {
        List<Aluno> alunosPendentes = alunoService.buscarAlunosPendentes(); //
        model.addAttribute("alunosPendentes", alunosPendentes);
        return "validar-postagens";
    }
}
