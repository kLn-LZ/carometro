package com.fatec.carometro.Services;

import com.fatec.carometro.DTOs.CursoDTO;
import com.fatec.carometro.DTOs.mappers.Mapper;
import com.fatec.carometro.Entities.Aluno;
import com.fatec.carometro.Entities.Curso;
import com.fatec.carometro.Entities.StatusValidacao;
import com.fatec.carometro.Exceptions.AlunoNotFoundException;
import com.fatec.carometro.Exceptions.CursoNotFoundException;
import com.fatec.carometro.Repositories.AlunoRepository;
import com.fatec.carometro.Repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional(readOnly = true)
    public List<Curso> buscarCursos() {
        return cursoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Curso buscarPorId(Long id) {

        return cursoRepository.findById(id)
                .orElseThrow(() -> new CursoNotFoundException("Curso com ID " + id + " não encontrado."));
    }
}
