package com.flight_search.repositories;

import com.flight_search.domain.entity.PostKey;
import com.flight_search.domain.entity.PostsEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThiredAPiRepository extends JpaRepository<PostsEntity, PostKey> {

}
