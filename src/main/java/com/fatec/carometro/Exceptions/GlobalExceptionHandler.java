package com.fatec.carometro.Exceptions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fatec.carometro.DTOs.CursoDTO;
import com.fatec.carometro.DTOs.mappers.Mapper;
import com.fatec.carometro.Entities.Curso;
import com.fatec.carometro.Services.CursoService;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger("TESTE");
   
    @Autowired
    private CursoService cursoService;
    
    @Autowired
    private Mapper<Curso, CursoDTO> cursoMapper;	


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
		List<Curso> cursos = cursoService.buscarCursos();
        model.addAttribute("cursos", cursoMapper.toDtoList(cursos));
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
