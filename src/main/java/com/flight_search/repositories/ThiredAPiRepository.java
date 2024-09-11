package com.flight_search.repositories;

import com.flight_search.domain.entity.PostKey;
import com.flight_search.domain.entity.PostsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link PostsEntity} entities identified by a composite key {@link PostKey}.
 * Extends {@link JpaRepository} to provide JPA-based data access operations for posts.
 */
@Repository
public interface ThiredAPiRepository extends JpaRepository<PostsEntity, PostKey> {

}
