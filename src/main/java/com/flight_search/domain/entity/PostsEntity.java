package com.flight_search.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EmbeddedId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

/**
 * Represents a post entity in the database with a composite key.
 */
@Profile("test")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class PostsEntity {

    /**
     * The composite key for the post entity.
     * This key is composed of {@link PostKey} which includes user ID and post ID.
     */
    @EmbeddedId
    private PostKey id;

    /**
     * The title of the post.
     */
    private String title;

    /**
     * The body content of the post.
     */
    private String body;

}
