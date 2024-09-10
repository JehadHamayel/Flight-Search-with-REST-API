package com.flight_search.services.impl;

import com.flight_search.domain.entity.PostsEntity;
import com.flight_search.mappers.Mapper;
import com.flight_search.repositories.ThiredAPiRepository;
import com.flight_search.services.ThiredApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThiredApiServiceImpl implements ThiredApiService {

    private final RestTemplate restTemplate;
    private final ThiredAPiRepository thiredAPiRepository;
    private final Mapper<PostsEntity, Map<String, Object>> postsMapper;

    String baseUrl = "https://jsonplaceholder.typicode.com/";
    StringBuilder urlStringBuilder = new StringBuilder(baseUrl);
    String POST = "posts";
    @Override
    public List<Map<String, Object>> getPosts() {
        String url = urlStringBuilder.append(POST).toString();
        HttpEntity<Void> httpEntity = new HttpEntity<>(getHeaders());
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET,httpEntity, List.class);

        saveAll(response);
        return response.getBody();
    }

    private void saveAll(ResponseEntity<List> response) {
        // Convert the JSON response to a list of PostEntity objects
        List<PostsEntity> postEntities = ((List<Map<String, Object>>) response.getBody()).stream()
                .map(postsMapper::mapFrom)
                .collect(Collectors.toList());

        thiredAPiRepository.saveAll(postEntities);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
