package com.flight_search.mappers.impl;

import com.flight_search.domain.entity.PostsEntity;
import com.flight_search.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Provides mapping functionality between {@link PostsEntity} and a {@link Map} of {@link String} to {@link Object}.
 * This class uses {@link ModelMapper} to convert between the entity representation of a post and a generic map.
 */
@Component
@RequiredArgsConstructor
public class PostsMapper implements Mapper<PostsEntity, Map<String, Object>> {

    private final ModelMapper modelMapper;

    /**
     * Converts a {@link PostsEntity} to a {@link Map} representation.
     * @param postsEntity the entity to be converted.
     * @return a map representing the entity.
     */
    @Override
    public Map<String, Object> mapTo(PostsEntity postsEntity) {
        return modelMapper.map(postsEntity, Map.class);
    }

    /**
     * Converts a {@link Map} representation to a {@link PostsEntity}.
     * @param postsDto the map to be converted.
     * @return the corresponding entity.
     */
    @Override
    public PostsEntity mapFrom(Map<String, Object> postsDto) {
        return modelMapper.map(postsDto, PostsEntity.class);
    }
}
