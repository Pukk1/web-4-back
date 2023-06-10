package com.ivan.web4back.model.access;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessRepository extends CrudRepository<AccessEntity, Long> {
    public Optional<AccessEntity> findByUsername(String username);
}
