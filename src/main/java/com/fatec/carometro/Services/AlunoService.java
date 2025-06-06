package com.fatec.carometro.Services;

import com.fatec.carometro.Entities.*;
import com.fatec.carometro.Exceptions.AlunoNotFoundException;
import com.fatec.carometro.Repositories.AlunoRepository;
import com.fatec.carometro.Repositories.CadastroAcademicoRepository;
import com.fatec.carometro.Repositories.ValidacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ValidacaoRepository validacaoRepository;

    @Autowired
    private CadastroAcademicoRepository cadastroAcademicoRepository;

    @Transactional(readOnly = true)
    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNotFoundException("Aluno com ID " + id + " não encontrado."));
    }

    @Transactional(readOnly = true)
    public List<Aluno> buscarAlunosPendentes() {
        return alunoRepository.findByValidacao_Status(StatusValidacao.PENDENTE);
    }

    @Transactional(readOnly = true)
    public List<Aluno> buscarAlunosAprovados() {
        return alunoRepository.findByValidacao_Status(StatusValidacao.APROVADO);
    }

    public void registraAluno(Aluno aluno) {

        cadastroAcademicoRepository.findByAluno_Id(aluno.getId()).ifPresent(c -> aluno.getCadastroAcademico().setId(c.getId()));
        validacaoRepository.findByAluno_Id(aluno.getId()).ifPresent( v -> aluno.getValidacao().setId(v.getId()));

        validacaoRepository.save(aluno.getValidacao());
        cadastroAcademicoRepository.save((aluno.getCadastroAcademico()));
    }
}
