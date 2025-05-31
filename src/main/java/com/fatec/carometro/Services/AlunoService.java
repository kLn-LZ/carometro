package com.fatec.carometro.Services;

import aj.org.objectweb.asm.commons.TryCatchBlockSorter;
import com.fatec.carometro.DTOs.AlunoDTO;
import com.fatec.carometro.Entities.*;
import com.fatec.carometro.Exceptions.AlunoNotFoundException;
import com.fatec.carometro.Repositories.AlunoRepository;
import com.fatec.carometro.Repositories.CadastroAcademicoRepository;
import com.fatec.carometro.Repositories.ValidacaoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    public void registraAluno(AlunoDTO alunoDTO, HttpSession session) throws IOException {
        if (!alunoDTO.consentePublicacao()) {
            throw new IllegalArgumentException("É necessário consentimento para publicação.");
        }

        CadastroAcademico cadastroAcademico = cadastroAcademicoRepository.findByAluno_Id(alunoDTO.id()).orElse(null);
        Validacao validacao = validacaoRepository.findByAluno_Id(alunoDTO.id()).orElse(null);

        Aluno aluno = alunoDTO.toEntity(cadastroAcademico, validacao);

        validacaoRepository.save(aluno.getValidacao());
        cadastroAcademicoRepository.save((aluno.getCadastroAcademico()));
    }
}
