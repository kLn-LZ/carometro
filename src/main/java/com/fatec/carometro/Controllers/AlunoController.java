package com.fatec.carometro.Controllers;

import java.io.IOException;
import java.util.List;

import com.fatec.carometro.Entities.Curso;
import com.fatec.carometro.Entities.Usuario;
import com.fatec.carometro.Exceptions.AlunoNotFoundException;
import com.fatec.carometro.Services.CursoService;
import com.fatec.carometro.Services.ValidacaoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fatec.carometro.DTOs.AlunoDTO;
import com.fatec.carometro.Entities.Aluno;
import com.fatec.carometro.Entities.StatusValidacao;
import com.fatec.carometro.Services.AlunoService;

import java.util.Base64;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AlunoController {

    @Autowired
    private AlunoService alunoService;
    @Autowired
    private ValidacaoService validacaoService;

    @Autowired
    private CursoService cursoService;

    @GetMapping("/registroAluno")
    public String mostraTelaRegistro(@RequestParam(value = "id", required = false) Long id, Model model, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        AlunoDTO alunoDTO;
        if (usuarioLogado.getId() != null) {
            alunoDTO = new AlunoDTO(alunoService.buscarPorId(usuarioLogado.getId()));
        } else {
            alunoDTO = new AlunoDTO(null, null, null, null, null, null, null, null, null, null, null, false, null, null, null, null);
        }

        List<Curso> cursos = cursoService.buscarCursos();
        model.addAttribute("cursos", cursos);
        model.addAttribute("alunoDTO", alunoDTO);
        return "registroAluno";
    }

    @PostMapping("/registroAluno")
    public String registraAluno(@ModelAttribute @Valid AlunoDTO alunoDTO, BindingResult result,
                                RedirectAttributes redirectAttributes, HttpSession session) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Por favor, corrija os erros no formulário.");
            return "redirect:/registroAluno";
        }

        if (alunoDTO.foto() == null || alunoDTO.foto().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "A foto é obrigatória.");
            return "redirect:/registroAluno";
        }

        try {
            alunoService.registraAluno(alunoDTO, session);
            redirectAttributes.addFlashAttribute("successMessage", "Aluno registrado com sucesso!");
            return "redirect:/registroSucesso";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao registrar aluno: " + e.getMessage());
            return "redirect:/registroAluno";
        }
    }

    @GetMapping("/validar-aluno/{id}")
    public String exibirValidacao(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Aluno aluno = alunoService.buscarPorId(id);
            AlunoDTO alunoDTO = new AlunoDTO(aluno);
            model.addAttribute("alunoDTO", alunoDTO);

            String fotoBase64 = aluno.getCadastroAcademico() != null && aluno.getCadastroAcademico().getFoto() != null
                    ? Base64.getEncoder().encodeToString(aluno.getCadastroAcademico().getFoto())
                    : "";

            model.addAttribute("fotoBase64", fotoBase64);
            return "validar-aluno";
        } catch (AlunoNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/validar-postagens";
        } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Erro inesperado: " + e.getMessage());
        return "redirect:/validar-postagens";
        }
    }

    @PostMapping("/validar-aluno")
    public String validarAluno(@ModelAttribute AlunoDTO alunoDTO,
                               RedirectAttributes redirectAttributes,
                               HttpSession session) {
        try {
            Usuario coordenador = (Usuario) session.getAttribute("usuarioLogado");

            if (coordenador == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Coordenador não está logado.");
                return "redirect:/login";
            }

            Long alunoId = alunoDTO.id();
            StatusValidacao status = alunoDTO.status();
            String descricaoReprovacao = alunoDTO.descricaoReprovacao();

            validacaoService.valida(alunoId, coordenador.getId(), status, descricaoReprovacao, true);
            redirectAttributes.addFlashAttribute("successMessage", "Validação realizada com sucesso!");
            return "redirect:/validar-postagens";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/validar-aluno/" + alunoDTO.id();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao validar aluno: " + e.getMessage());
            return "redirect:/validar-postagens";
        }
    }

    @GetMapping("/validar-postagens")
    public String listarPostagensPendentes(Model model) {
        List<Aluno> alunosPendentes = alunoService.buscarAlunosPendentes();

        List<AlunoDTO> alunosPendentesDTO = alunosPendentes.stream()
                .map(AlunoDTO::new) // Use the AlunoDTO constructor directly
                .collect(Collectors.toList());

        model.addAttribute("alunosPendentes", alunosPendentesDTO);
        return "validar-postagens";
    }
    
    @GetMapping("/feed")
    public String listarAlunosAprovados(Model model){
    	model.addAttribute("alunos", alunoService.buscarAlunosAprovados());
    	return "feed";
    }
    
    @GetMapping("/aluno/{id}/foto")
    public ResponseEntity<byte[]> getFoto(@PathVariable Long id) {
        Aluno aluno = alunoService.buscarPorId(id);
        
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(aluno.getCadastroAcademico().getFoto());
    }
}
