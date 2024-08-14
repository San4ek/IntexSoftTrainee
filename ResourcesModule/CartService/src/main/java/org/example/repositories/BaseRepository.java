package org.example.repositories;

import org.example.exceptions.ObjectNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T,UUID> extends JpaRepository<T, UUID> {

    @Override
    default <S extends T> S save(final S entity) {
        return saveAndFlush(entity);
    }

    @Override
    default T getById(final UUID uuid) {
        return findById(uuid).orElseThrow(() -> new ObjectNotFoundException("Object not found with id " + uuid));
    }
}
