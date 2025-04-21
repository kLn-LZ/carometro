package com.fatec.carometro.Controllers;

import com.fatec.carometro.Entities.Usuario;
import com.fatec.carometro.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "lista";
    }

    @GetMapping("/novo")
    public String novoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "formulario";
    }

    @PostMapping
    public String salvar(@ModelAttribute Usuario usuario) {
        usuarioService.salvar(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        model.addAttribute("usuario", usuario);
        return "formulario";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        usuarioService.excluir(id);
        return "redirect:/usuarios";
    }
}
