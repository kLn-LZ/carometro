package com.fatec.carometro.Controllers;

import com.fatec.carometro.DTOs.AlunoDTO;
import com.fatec.carometro.Entities.Aluno;
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
import java.util.List;

@Controller
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping("/registroAluno")
    public String mostraTelaRegistro(Model model) {
        model.addAttribute("alunoDTO", new AlunoDTO(null, null, null, null, null, null, null, null, false, null));
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
        return "validar-aluno";
    }

    @PostMapping("/validar-aluno")
    public String validarAluno(@RequestParam Long id) {
        alunoService.validar(id); // método que define como validado no banco
        return "redirect:/validar-postagens";
    }

    @GetMapping("/validar-postagens")
    public String listarPostagensPendentes(Model model) {
        List<Aluno> alunosPendentes = alunoService.buscarAlunosPendentes(); //
        model.addAttribute("alunosPendentes", alunosPendentes);
        return "validar-postagens";
    }
}
