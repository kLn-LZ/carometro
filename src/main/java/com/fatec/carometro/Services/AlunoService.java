package com.fatec.carometro.Services;

import com.fatec.carometro.DTOs.AlunoDTO;
import com.fatec.carometro.DTOs.AlunoDTO;
import com.fatec.carometro.Entities.Aluno;
import com.fatec.carometro.Entities.Validacao;
import com.fatec.carometro.Entities.Validacao;
import com.fatec.carometro.Repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
    }

    public List<Aluno> buscarAlunosPendentes() {
        return alunoRepository.findByValidado(Validacao.PENDENTE);
    }

    public Aluno registraAluno(Aluno aluno) {
        if (!aluno.isConsentePublicacao()) throw new RuntimeException("Precisa-se de consentimento para publicação");
        aluno.setValidado(Validacao.PENDENTE);
        Aluno alunoRegistrado = alunoRepository.save(aluno);
        return alunoRegistrado;
    }

    public Aluno valida(Aluno aluno) {
        Optional<Aluno> alunoExistenteOpt = alunoRepository.findById(aluno.getId());
        if (alunoExistenteOpt.isEmpty()) {
            throw new RuntimeException("Aluno com ID " + aluno.getId() + " não encontrado.");
        }

        Aluno alunoExistente = alunoExistenteOpt.get();

        alunoExistente.setValidado(aluno.getValidado());
        alunoExistente.setDescricaoReprovacao(aluno.getDescricaoReprovacao());

        return alunoRepository.save(alunoExistente);
    }

    public AlunoDTO getAlunoDTOById(Long id) {
        return null;
    }
    
    public List<Aluno> buscarAlunosAprovados(){
    	return alunoRepository.findByValidado(Validacao.APROVADO);
    }
}
