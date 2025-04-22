package com.fatec.carometro.Controllers;

import com.fatec.carometro.DTOs.AlunoDTO;
import com.fatec.carometro.Entities.Aluno;
import com.fatec.carometro.Entities.Validacao;
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
    public String mostraTelaRegistro(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null) {
            AlunoDTO alunoDTO = alunoService.getAlunoDTOById(id);
            model.addAttribute("alunoDTO", alunoDTO);
        } else {
            model.addAttribute("alunoDTO", new AlunoDTO(null, null, null,null,null,null,null,null,null,null,null,null,null));
        }
        return "registroAluno";
    }

    @PostMapping("/registroAluno")
    public String registraAluno(@ModelAttribute @Valid AlunoDTO alunoDTO,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors())
            throw new RuntimeException("Por favor, corrija os erros no formulário.");

        if (alunoDTO.foto() == null || alunoDTO.foto().isEmpty())
            throw new RuntimeException("A foto é obrigatória.");

        try {
            Aluno aluno = alunoDTO.toEntity();
            alunoService.registraAluno(aluno);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "registroSucesso";
    }

    @GetMapping("/validar-aluno/{id}")
    public String exibirValidacao(@PathVariable Long id, Model model) {
        Aluno aluno = alunoService.buscarPorId(id);
        model.addAttribute("aluno", aluno);
        String fotoBase64 = Base64.getEncoder().encodeToString(aluno.getFoto());
        model.addAttribute("fotoBase64", fotoBase64);
        return "validar-aluno";
    }

    @PostMapping("/validar-aluno")
    public String validarAluno(@ModelAttribute Aluno aluno) {
        if (aluno.getValidado() == Validacao.REPROVADO && (aluno.getDescricaoReprovacao() == null || aluno.getDescricaoReprovacao().isEmpty())) {
            return "redirect:/validar-aluno?erro=descricaoReprovacao";
        }
        alunoService.valida(aluno);
        return "redirect:/validar-postagens";
    }

    @GetMapping("/validar-postagens")
    public String listarPostagensPendentes(Model model) {
        List<Aluno> alunosPendentes = alunoService.buscarAlunosPendentes(); //
        model.addAttribute("alunosPendentes", alunosPendentes);
        return "validar-postagens";
    }
}
