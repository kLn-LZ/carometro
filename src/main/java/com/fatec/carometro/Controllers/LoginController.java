package com.fatec.carometro.Controllers;

import com.fatec.carometro.DTOs.UsuarioDTO;
import com.fatec.carometro.DTOs.mappers.Mapper;
import com.fatec.carometro.Entities.Coordenador;
import com.fatec.carometro.Entities.Usuario;
import com.fatec.carometro.Exceptions.LoginException;
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

    @Autowired
    private Mapper<Usuario, UsuarioDTO> usuarioMapper;

    @GetMapping("/")
    public String telaInicial() {
        return "login";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String processarLogin(@RequestParam String email, @RequestParam String senha, HttpSession session, Model model) {
        UsuarioDTO dto = usuarioMapper.entityToDto(usuarioService.autenticar(email, senha));
        session.setAttribute("usuarioLogado", dto);
        if (dto.tipo().equals("COORDENADOR"))
            return "redirect:menu-adm";
        else
            return "redirect:menu-aluno";
    }

    @GetMapping("/visitante")
    public String visitante() {
        return "redirect:/feed";
    }

    @GetMapping("/menu-adm")
    public String menuAdm(HttpSession session, Model model){
        UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuarioLogado");
        if (usuarioDTO == null || !usuarioDTO.tipo().equals("COORDENADOR"))
            throw new LoginException("Acesso Negado!");
        return "menu-adm";
    }

    @GetMapping("/menu-aluno")
    public String menuAluno(HttpSession session, Model model){
        UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuarioLogado");
        if (usuarioDTO == null || !usuarioDTO.tipo().equals("ALUNO"))
            throw new LoginException("Acesso Negado!");
        return "menu-aluno";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}