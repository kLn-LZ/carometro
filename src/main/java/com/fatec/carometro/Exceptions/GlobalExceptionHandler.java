package com.fatec.carometro.Exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request, Model model) {
        model.addAttribute("aluno", ex.getBindingResult().getTarget());
        return request.getRequestURL().insert(0, "redirect:").toString();
    }

    @ExceptionHandler(LoginException.class)
    public String handleLoginException(Exception ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "login";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, HttpServletRequest request, Model model) {
        model.addAttribute("error", "Ocorreu um erro inesperado: " + ex.getMessage());
        return request.getRequestURL().insert(0, "redirect:").toString();
    }
}
