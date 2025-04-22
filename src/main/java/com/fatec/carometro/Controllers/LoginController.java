package com.fatec.carometro.Controllers;

import com.fatec.carometro.Entities.TipoUsuario;
import com.fatec.carometro.Entities.Usuario;
import com.fatec.carometro.Services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String processarLogin(@RequestParam String email, @RequestParam String senha, HttpSession session, Model model) {
        Usuario usuario = usuarioService.autenticar(email, senha);
        session.setAttribute("usuarioLogado", usuario);
        if (usuario.getTipo() == TipoUsuario.ADMIN)
            return "menu-adm";
        else
            return "menu-aluno";
    }

    @GetMapping("/visitante")
    public String visitante() {
        return "redirect:/feed";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}