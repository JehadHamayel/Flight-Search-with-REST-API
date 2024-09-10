package com.flight_search.mappers.impl;

import com.flight_search.domain.entity.PostsEntity;
import com.flight_search.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PostsMapper implements Mapper<PostsEntity, Map<String, Object>> {

    private final ModelMapper modelMapper;

    @Override
    public Map<String, Object> mapTo(PostsEntity postsEntity) {
        return modelMapper.map(postsEntity, Map.class);
    }

    @Override
    public PostsEntity mapFrom(Map<String, Object> postsDto) {
        return modelMapper.map(postsDto, PostsEntity.class);
    }
}
