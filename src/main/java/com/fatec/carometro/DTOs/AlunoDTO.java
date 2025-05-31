package com.fatec.carometro.DTOs;

import com.fatec.carometro.Entities.*;
import com.fatec.carometro.Services.CadastroAcademicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public record AlunoDTO(
        Long id,
        String nome,
        String email,
        Long cursoId,
        String cursoNome,
        Integer ano,
        String linkedIn,
        String gitHub,
        String lattes,
        String historico,
        String comentarios,
        Boolean consentePublicacao,
        MultipartFile foto,
        String fotoBase64,
        StatusValidacao status,
        String descricaoReprovacao
)  {

    public AlunoDTO(Aluno aluno){
        this(
            aluno.getId(),
            aluno.getNome(),
            aluno.getEmail(),
            aluno.getCurso() != null ? aluno.getCurso().getId() : null,
            aluno.getCurso() != null ? aluno.getCurso().getNome() : null,
            aluno.getCadastroAcademico() != null ? aluno.getCadastroAcademico().getAno() : null,
            aluno.getCadastroAcademico() != null ? aluno.getCadastroAcademico().getLinkedIn() : null,
            aluno.getCadastroAcademico() != null ? aluno.getCadastroAcademico().getGitHub() : null,
            aluno.getCadastroAcademico() != null ? aluno.getCadastroAcademico().getLattes() : null,
            aluno.getCadastroAcademico() != null ? aluno.getCadastroAcademico().getHistorico() : null,
            aluno.getCadastroAcademico() != null ? aluno.getCadastroAcademico().getComentarios() : null,
            aluno.getValidacao() != null? aluno.getValidacao().isConsentePublicacao(): null,
            null,
            aluno.getCadastroAcademico() != null && aluno.getCadastroAcademico().getFoto() != null
                    ? Base64.getEncoder().encodeToString(aluno.getCadastroAcademico().getFoto())
                    : null,
            aluno.getValidacao() != null ? aluno.getValidacao().getStatus() : null,
            aluno.getValidacao() != null ? aluno.getValidacao().getDescricaoReprovacao() : null
        );
    }

    public Aluno toEntity(CadastroAcademico cadastroAcademico, Validacao validacao) throws IOException, NumberFormatException {
        Aluno aluno = new Aluno();
        aluno.setId(this.id());
        aluno.setNome(this.nome());
        aluno.setEmail(this.email);

        if (this.cursoId() != null) {
            Curso curso = new Curso();
            curso.setId(this.cursoId());

            aluno.setCurso(curso);
        }


        CadastroAcademico cadastro = (cadastroAcademico == null? new CadastroAcademico(): cadastroAcademico);
        cadastro.setAno(this.ano());
        cadastro.setLinkedIn(this.linkedIn());
        cadastro.setGitHub(this.gitHub());
        cadastro.setLattes(this.lattes());
        cadastro.setHistorico(this.historico());
        cadastro.setComentarios(this.comentarios());
        if (this.foto() != null && !this.foto().isEmpty()) {
            cadastro.setFoto(this.foto().getBytes());
        }
        cadastro.setAluno(aluno);
        aluno.setCadastroAcademico(cadastro);

        Validacao validacao2 = (validacao == null? new Validacao(aluno, java.time.LocalDate.now()): validacao);

        validacao2.setConsentePublicacao(Boolean.TRUE.equals(this.consentePublicacao()));
        validacao2.setStatus(StatusValidacao.PENDENTE);
        validacao2.setDescricaoReprovacao(null);
        validacao2.setAluno(aluno);
        aluno.setValidacao(validacao);

        return aluno;
    }
}
