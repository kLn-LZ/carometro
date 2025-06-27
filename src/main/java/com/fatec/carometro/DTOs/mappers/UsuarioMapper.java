package com.fatec.carometro.DTOs.mappers;

import com.fatec.carometro.DTOs.UsuarioDTO;
import com.fatec.carometro.Entities.Aluno;
import com.fatec.carometro.Entities.Coordenador;
import com.fatec.carometro.Entities.Curso;
import com.fatec.carometro.Entities.Usuario;
import jakarta.persistence.DiscriminatorValue;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper implements Mapper<Usuario, UsuarioDTO> {

    @Override
    public UsuarioDTO entityToDto(Usuario entity) {
        return new UsuarioDTO(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getSenha(),
                entity.getCurso() != null ? entity.getCurso().getId() : null,
                entity.getClass().getAnnotation(DiscriminatorValue.class) != null
                        ? entity.getClass().getAnnotation(DiscriminatorValue.class).value()
                        : null,
                entity.getToken()
        );
    }

    @Override
    public Usuario dtoToEntity(UsuarioDTO dto) {
        Usuario usuario = switch (dto.tipo()) {
            case "ALUNO" -> new Aluno();
            case "COORDENADOR" -> new Coordenador();
            default -> throw new IllegalArgumentException("Tipo de usuário inválido: " + dto.tipo());
        };

        usuario.setId(dto.id());
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setToken(dto.token());

        if (dto.cursoId() != null) {
            Curso curso = new Curso();
            curso.setId(dto.cursoId());
            usuario.setCurso(curso);
        }

        return usuario;
    }
}
