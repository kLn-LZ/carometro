package com.fatec.carometro.DTOs;

import com.fatec.carometro.Entities.Aluno;
import com.fatec.carometro.Entities.Coordenador;
import com.fatec.carometro.Entities.Curso;
import com.fatec.carometro.Entities.Usuario;
import jakarta.persistence.DiscriminatorValue;

import java.util.List;

public record UsuarioDTO(
        Long id,
        String nome,
        String email,
        String senha,
        Long cursoId,
        String tipo
) {
}
