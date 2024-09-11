package com.flight_search.repositories;

import com.flight_search.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link UserEntity} entities.
 * Extends {@link JpaRepository} to provide JPA-based data access operations for users.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Finds a user by their email address.
     * @param email the email address of the user to be found.
     * @return an {@link Optional} containing the {@link UserEntity} if found, or {@link Optional#empty()} if not.
     */
    Optional<UserEntity> findByEmail(String email);

}
