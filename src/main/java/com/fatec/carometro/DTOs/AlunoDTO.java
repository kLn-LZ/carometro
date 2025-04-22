package com.fatec.carometro.DTOs;

import com.fatec.carometro.Entities.Aluno;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public record AlunoDTO(
        @NotBlank String nome,
        @NotBlank String curso,
        @NotNull Integer ano,
        String linkedIn,
        String gitHub,
        String lattes,
        @NotBlank String historico,
        @NotBlank String conquistas,
        Boolean consentePublicacao,
        @NotNull MultipartFile foto
)  {

    public AlunoDTO(Aluno aluno){
        this(aluno.getNome(),
                aluno.getCurso(),
                aluno.getAno(),
                aluno.getLinkedIn(),
                aluno.getGitHub(),
                aluno.getLattes(),
                aluno.getHistorico(),
                aluno.getConquistas(),
                aluno.isConsentePublicacao(),
               null);
    }

    public Aluno toEntity() throws IOException, NumberFormatException {
        Aluno aluno = new Aluno();
        aluno.setNome(this.nome());
        aluno.setCurso(this.curso());
        aluno.setAno(ano);
        aluno.setLinkedIn(this.linkedIn());
        aluno.setGitHub(this.gitHub());
        aluno.setLattes(this.lattes());
        aluno.setHistorico(this.historico());
        aluno.setConquistas(this.conquistas());
        aluno.setConsentePublicacao(Boolean.TRUE.equals(this.consentePublicacao()));
        aluno.setFoto(this.foto().getBytes());
        return aluno;
    }
}
