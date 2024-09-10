package com.flight_search.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PostKey implements Serializable {

    private Long userId;
    private Long id;

}

