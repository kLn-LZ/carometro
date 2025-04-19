package com.fatec.carometro.Controllers;

import com.fatec.carometro.Entities.Aluno;
import com.fatec.carometro.Services.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping("/registro")
    public String mostraTelaRegistro(Model model) {
        model.addAttribute("aluno", new Aluno());

        return "registroAluno";
    }

    @PostMapping("/registro")
    public String registraAluno(@ModelAttribute Aluno aluno,
                                @RequestParam("foto") MultipartFile foto,
                                Model model) throws IOException {
        try {
            if (!foto.isEmpty()) {
                aluno.setFoto(foto.getBytes());
            }
            alunoService.registraAluno(aluno);
            return "redirect:/registroAluno?success";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("aluno", aluno);
            return "registroAluno";
        }
    }
}
