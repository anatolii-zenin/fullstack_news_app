package com.mjc.school.repository;

import com.mjc.school.repository.model.implementation.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    Optional<AuthorEntity> readAuthorByNewsId(Long newsId);
}
