package me.inqu1sitor.authservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    @Override
    default <S extends T> S save(S entity) {
        return saveAndFlush(entity);
    }
}
