package com.flight_search.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import org.springframework.context.annotation.Profile;

/**
 * Represents a composite key for an entity involving a user and a post.
 * This class is used as an embedded key for identifying a specific post associated with a user.
 */
@Profile("test")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PostKey implements Serializable {

    /**
     * The unique identifier of the user associated with the post.
     */
    private Long userId;

    /**
     * The unique identifier of the post.
     */
    private Long id;

}
