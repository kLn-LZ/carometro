package com.fatec.carometro.Controllers;

import com.fatec.carometro.DTOs.UsuarioDTO;
import com.fatec.carometro.Entities.Curso;
import com.fatec.carometro.Entities.Usuario;
import com.fatec.carometro.Services.CursoService;
import com.fatec.carometro.Services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "lista";
    }

    @GetMapping("/novo")
    public String novoUsuario(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO(null, null, null, null, null, null));
        List<Curso> cursos = cursoService.buscarCursos();
        model.addAttribute("cursos", cursos);
        return "formulario";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Curso> cursos = cursoService.buscarCursos();
            model.addAttribute("cursos", cursos);
            return "formulario";
        }

        usuarioService.salvar(usuarioDTO);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        model.addAttribute("usuarioDTO", new UsuarioDTO(usuario));
        List<Curso> cursos = cursoService.buscarCursos();
        model.addAttribute("cursos", cursos);
        return "formulario";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        usuarioService.excluir(id);
        return "redirect:/usuarios";
    }
}
