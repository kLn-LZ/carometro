package com.fatec.carometro.DTOs;

import com.fatec.carometro.Entities.Aluno;
import com.fatec.carometro.Entities.Coordenador;
import com.fatec.carometro.Entities.Curso;
import com.fatec.carometro.Entities.Usuario;
import jakarta.persistence.DiscriminatorValue;

public record UsuarioDTO(
        Long id,
        String nome,
        String email,
        String senha,
        Long cursoId,
        String tipo
) {

    public UsuarioDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getCurso() != null ? usuario.getCurso().getId() : null,
                usuario.getClass().getAnnotation(DiscriminatorValue.class) != null
                        ? usuario.getClass().getAnnotation(DiscriminatorValue.class).value()
                        : null
        );
    }

    public Usuario toEntity() {
        Usuario usuario;
        switch (this.tipo()) {
            case "ALUNO":
                usuario = new Aluno();
                break;
            case "COORDENADOR":
                usuario = new Coordenador();
                break;
            default:
                throw new IllegalArgumentException("Tipo de usuário inválido: " + this.tipo());
        }

        usuario.setId(this.id());
        usuario.setNome(this.nome());
        usuario.setEmail(this.email());
        usuario.setSenha(this.senha());

        if (this.cursoId() != null) {
            Curso curso = new Curso();
            curso.setId(this.cursoId());
            usuario.setCurso(curso);
        }

        return usuario;
    }
}
