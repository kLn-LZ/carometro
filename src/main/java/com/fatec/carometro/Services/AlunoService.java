package com.fatec.carometro.Services;

import com.fatec.carometro.Entities.Aluno;
import com.fatec.carometro.Repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;


    public Aluno registraAluno(Aluno aluno) {
        if (!aluno.isConsentePublicacao()) throw new RuntimeException("Precisa-se de consentimento para publicação");

        Aluno alunoRegistrado = alunoRepository.save(aluno);

        return alunoRegistrado;
    }
}
