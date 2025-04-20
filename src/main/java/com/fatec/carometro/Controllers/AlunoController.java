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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

            alunoService.registraAluno(aluno);
            return "registroSucesso";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "registroAluno";
        }
    }
}
