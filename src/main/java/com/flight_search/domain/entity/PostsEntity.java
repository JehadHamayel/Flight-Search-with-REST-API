package com.flight_search.domain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EmbeddedId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class PostsEntity {

    @EmbeddedId
    private PostKey id;

    private String title;
    private String body;



}
