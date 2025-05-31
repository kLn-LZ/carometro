package com.fatec.carometro.Services;

import com.fatec.carometro.Entities.*;
import com.fatec.carometro.Repositories.AlunoRepository;
import com.fatec.carometro.Repositories.CadastroAcademicoRepository;
import com.fatec.carometro.Repositories.CoordenadorRepository;
import com.fatec.carometro.Repositories.ValidacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ValidacaoService {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private CoordenadorRepository coordenadorRepository;
    @Autowired
    private ValidacaoRepository validacaoRepository;

    @Transactional
    public Validacao valida(Long alunoId, Long coordenadorId, StatusValidacao status,
                            String descricaoReprovacao, boolean consentePublicacao) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno com ID " + alunoId + " não encontrado."));

        Coordenador coordenador = coordenadorRepository.findById(coordenadorId)
                .orElseThrow(() -> new RuntimeException("Coordenador com ID " + coordenadorId + " não encontrado."));

        if (status == StatusValidacao.REPROVADO &&
                (descricaoReprovacao == null || descricaoReprovacao.trim().isEmpty())) {
            throw new IllegalArgumentException("Motivo da reprovação é obrigatório.");
        }

        Validacao validacao = aluno.getValidacao();

        validacao.setCoordenador(coordenador);
        validacao.setStatus(status);
        validacao.setDescricaoReprovacao(status == StatusValidacao.REPROVADO ? descricaoReprovacao : null);
        validacao.setDataAprovacao(status != StatusValidacao.PENDENTE ? LocalDate.now() : null);

        return validacaoRepository.save(validacao);
    }

    public Validacao buscarPorAlunoId(Long id) {
        return validacaoRepository.findByAluno_Id(id).orElseThrow(() -> new RuntimeException("Validação não encontrada."));
    }
}
