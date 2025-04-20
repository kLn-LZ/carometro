package com.fatec.carometro.Exceptions;

import com.fatec.carometro.Entities.Aluno;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex, Model model) {
        model.addAttribute("error", "Por favor, corrija os erros no formulário.");
        model.addAttribute("aluno", ex.getBindingResult().getTarget());
        return "registroAluno";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("error", "Ocorreu um erro inesperado: " + ex.getMessage());
        model.addAttribute("aluno", new Aluno());
        return "registroAluno";
    }
}
