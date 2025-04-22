package com.fatec.carometro.DTOs;

import com.fatec.carometro.Entities.Aluno;
import com.fatec.carometro.Entities.Validacao;
import com.fatec.carometro.Utils.CustomMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public record AlunoDTO(
        Long id,
        String nome,
        String curso,
        Integer ano,
        String linkedIn,
        String gitHub,
        String lattes,
        String historico,
        String comentarios,
        Boolean consentePublicacao,
        MultipartFile foto,
        Validacao validado,
        String descricaoReprovacao
)  {

    public AlunoDTO(Aluno aluno){
        this(aluno.getId(),
                aluno.getNome(),
                aluno.getCurso(),
                aluno.getAno(),
                aluno.getLinkedIn(),
                aluno.getGitHub(),
                aluno.getLattes(),
                aluno.getHistorico(),
                aluno.getComentarios(),
                aluno.isConsentePublicacao(),
                new CustomMultipartFile(aluno.getFoto(), "foto", null, "image/jpeg"),
                aluno.getValidado(),
                aluno.getDescricaoReprovacao());
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
        aluno.setComentarios(this.comentarios());
        aluno.setConsentePublicacao(Boolean.TRUE.equals(this.consentePublicacao()));
        aluno.setFoto(this.foto().getBytes());
        aluno.setValidado(this.validado());
        aluno.setDescricaoReprovacao(this.descricaoReprovacao());
        return aluno;
    }
}
