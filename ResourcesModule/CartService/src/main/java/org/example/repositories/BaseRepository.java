package org.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T,UUID> extends JpaRepository<T, UUID> {

    @Override
    default <S extends T> S save(S entity) {
        return saveAndFlush(entity);
    }

    @Override
    default T getById(UUID uuid) {
        return findById(uuid).orElseThrow();
    }
}
