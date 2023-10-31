package com.rolanmunoz.whatscooking.application.mapper;

import java.util.List;

public interface EntityMapper <D, E> {

    E toEntity(D dtp);
    D toDto(E entity);
    List<E> toEntity(List<D> dtoList);
    List<D> toDto(List<E> entityList);
}
