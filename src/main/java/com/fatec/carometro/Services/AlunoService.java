package com.fatec.carometro.Services;

import com.fatec.carometro.DTOs.AlunoDTO;
import com.fatec.carometro.Entities.Aluno;
import com.fatec.carometro.Exceptions.CadastroAlunoException;
import com.fatec.carometro.Repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
    }

    public List<Aluno> buscarAlunosPendentes() {
        return alunoRepository.findByValidadoFalse();
    }

    public Aluno registraAluno(Aluno aluno) {
        if (!aluno.isConsentePublicacao()) throw new CadastroAlunoException(new AlunoDTO(aluno), "Precisa-se de consentimento para publicação");
        return alunoRepository.save(aluno);
    }

    public void validar(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
        aluno.setConsentePublicacao(true);
        alunoRepository.save(aluno);
    }
}
