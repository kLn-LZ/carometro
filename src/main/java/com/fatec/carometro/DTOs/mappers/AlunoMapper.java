package com.fatec.carometro.DTOs.mappers;

import com.fatec.carometro.DTOs.AlunoDTO;
import com.fatec.carometro.Entities.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

@Component
public class AlunoMapper implements Mapper<Aluno, AlunoDTO> {
    @Override
    public AlunoDTO entityToDto(Aluno entity) {
        return new AlunoDTO(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getCurso() != null ? entity.getCurso().getId() : null,
                entity.getCurso() != null ? entity.getCurso().getNome() : null,
                entity.getCadastroAcademico() != null ? entity.getCadastroAcademico().getAno() : null,
                entity.getCadastroAcademico() != null ? entity.getCadastroAcademico().getLinkedIn() : null,
                entity.getCadastroAcademico() != null ? entity.getCadastroAcademico().getGitHub() : null,
                entity.getCadastroAcademico() != null ? entity.getCadastroAcademico().getLattes() : null,
                entity.getCadastroAcademico() != null ? entity.getCadastroAcademico().getHistorico() : null,
                entity.getCadastroAcademico() != null ? entity.getCadastroAcademico().getComentarios() : null,
                entity.getValidacao() != null? entity.getValidacao().isConsentePublicacao(): null,
                null,
                entity.getCadastroAcademico() != null && entity.getCadastroAcademico().getFoto() != null
                        ? Base64.getEncoder().encodeToString(entity.getCadastroAcademico().getFoto())
                        : null,
                entity.getValidacao() != null ? entity.getValidacao().getStatus() : null,
                entity.getValidacao() != null ? entity.getValidacao().getDescricaoReprovacao() : null
        );
    }

    @Override
    public Aluno dtoToEntity(AlunoDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setId(dto.id());
        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());

        if (dto.cursoId() != null) {
            Curso curso = new Curso();
            curso.setId(dto.cursoId());

            aluno.setCurso(curso);
        }

        CadastroAcademico cadastro = getCadastroAcademico(dto, aluno);
        aluno.setCadastroAcademico(cadastro);

        Validacao validacao = new Validacao(aluno, java.time.LocalDate.now());
        validacao.setConsentePublicacao(Boolean.TRUE.equals(dto.consentePublicacao()));
        validacao.setStatus(StatusValidacao.PENDENTE);
        validacao.setDescricaoReprovacao(null);
        validacao.setAluno(aluno);
        aluno.setValidacao(validacao);

        return aluno;
    }

    private static CadastroAcademico getCadastroAcademico(AlunoDTO dto, Aluno aluno) {
        CadastroAcademico cadastro = new CadastroAcademico();
        cadastro.setAno(dto.ano());
        cadastro.setLinkedIn(dto.linkedIn());
        cadastro.setGitHub(dto.gitHub());
        cadastro.setLattes(dto.lattes());
        cadastro.setHistorico(dto.historico());
        cadastro.setComentarios(dto.comentarios());
        if (dto.foto() != null && !dto.foto().isEmpty()) {
            try {
                cadastro.setFoto(dto.foto().getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        cadastro.setAluno(aluno);
        return cadastro;
    }
}
