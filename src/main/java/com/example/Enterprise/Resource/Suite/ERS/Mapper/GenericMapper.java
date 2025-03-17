package com.example.Enterprise.Resource.Suite.ERS.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface GenericMapper<E, D> {

    GenericMapper INSTANCE = Mappers.getMapper(GenericMapper.class);

    D toDto(E entity);

    E toEntity(D dto);

    List<D> toDtoList(List<E> entityList);

    List<E> toEntityList(List<D> dtoList);
}

