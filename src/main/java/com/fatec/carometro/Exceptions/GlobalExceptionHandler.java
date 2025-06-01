package com.fatec.carometro.Exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger("TESTE");


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request, Model model) {
        model.addAttribute("aluno", ex.getBindingResult().getTarget());
        return request.getRequestURL().insert(0, "redirect:").toString();
    }

    @ExceptionHandler(LoginException.class)
    public String handleLoginExceptions(LoginException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "redirect:login";
    }

    @ExceptionHandler(CadastroAlunoException.class)
    public String handleCadastroAlunoExceptions(CadastroAlunoException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("alunoDTO", ex.getAlunoDTO());
        return "registroAluno";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericExceptions(Exception ex, HttpServletRequest request, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "erro";
    }
}
