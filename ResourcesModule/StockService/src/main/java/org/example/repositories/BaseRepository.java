package org.example.repositories;

import org.example.entities.BrandEntity;
import org.example.exceptions.BrandNotExistException;
import org.example.exceptions.ObjectNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, UUID> extends
        JpaRepository<T, UUID>,
        QuerydslPredicateExecutor<T> {

    @Override
    default <S extends T> S save(S entity) {
        return saveAndFlush(entity);
    }

    @Override
    default T getById(UUID uuid) {
        return findById(uuid).orElseThrow(() -> new ObjectNotFoundException("Object not found with id " + uuid));
    }

}
