package com.fatec.carometro.DTOs.mappers;

import com.fatec.carometro.DTOs.CursoDTO;
import com.fatec.carometro.Entities.Curso;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper implements Mapper<Curso, CursoDTO>{
    @Override
    public CursoDTO entityToDto(Curso entity) {
        return new CursoDTO(entity.getId(), entity.getNome());
    }

    @Override
    public Curso dtoToEntity(CursoDTO dto) {
        return new Curso(dto.id(), dto.nome());
    }
}
