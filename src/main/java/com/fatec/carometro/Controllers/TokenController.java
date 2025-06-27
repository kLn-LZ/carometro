package com.fatec.carometro.Controllers;

import com.fatec.carometro.DTOs.UsuarioDTO;
import com.fatec.carometro.Entities.CadastroToken;
import com.fatec.carometro.Exceptions.LoginException;
import com.fatec.carometro.Services.TokenService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/tokens/generate")
    public String mostraGeradorDeTokens(HttpSession session) {
        UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuarioLogado");
        if (usuarioDTO == null || usuarioDTO.tipo().equals("ALUNO"))
            throw new RuntimeException("Acesso Negado!");
        return "generate_tokens";
    }

    @PostMapping("/tokens/generate")
    public String geraTokens(@RequestParam("quantidade") int quantidade,
                             @RequestParam(value = "diasParaExpirar", required = false) Integer diasParaExpirar,
                             Model model) {
        List<String> tokens = tokenService.geraTokens(quantidade, diasParaExpirar);
        model.addAttribute("tokens", tokens);
        return "generate_tokens_result";
    }

    @GetMapping("/tokens")
    public String listTokens(Model model, HttpSession session) {
        UsuarioDTO usuarioDTO = (UsuarioDTO) session.getAttribute("usuarioLogado");
        if (usuarioDTO == null || usuarioDTO.tipo().equals("ALUNO"))
            throw new RuntimeException("Acesso Negado!");

        List<CadastroToken> tokens = tokenService.listaTokens();
        model.addAttribute("tokens", tokens);
        return "list_tokens";
    }
}