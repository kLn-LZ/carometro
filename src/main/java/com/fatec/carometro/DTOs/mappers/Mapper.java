package com.fatec.carometro.DTOs.mappers;

import java.util.List;

public interface Mapper<E, D> {

    D entityToDto(E entity);
    E dtoToEntity(D dto);

    default List<D> toDtoList(List<E> entities){
        return entities.stream().map(this::entityToDto).toList();
    }

    default List<E> toEntitiesList(List<D> dtos){
        return dtos.stream().map(this::dtoToEntity).toList();
    }

}
