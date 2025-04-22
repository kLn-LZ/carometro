package com.fatec.carometro.Exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request, Model model) {
        model.addAttribute("aluno", ex.getBindingResult().getTarget());
        return request.getRequestURL().insert(0, "redirect:").toString();
    }

    @ExceptionHandler(LoginException.class)
    public String handleLoginExceptions(LoginException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "login";
    }

    @ExceptionHandler(CadastroAlunoException.class)
    public String handleCadastroAlunoExceptions(CadastroAlunoException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("alunoDTO", ex.getAlunoDTO());
        return "registroAluno";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericExceptions(Exception ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Ocorreu um erro inesperado: " + ex.getMessage());
        return "redirect:" + request.getRequestURI();

    }
}
