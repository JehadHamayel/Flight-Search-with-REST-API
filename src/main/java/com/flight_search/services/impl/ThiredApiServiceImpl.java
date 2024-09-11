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

/**
 * Implementation of the {@link ThiredApiService} interface.
 * Provides methods to fetch posts from an external API and save them to the repository.
 */
@Service
@RequiredArgsConstructor
public class ThiredApiServiceImpl implements ThiredApiService {

    private final RestTemplate restTemplate;
    private final ThiredAPiRepository thiredAPiRepository;
    private final Mapper<PostsEntity, Map<String, Object>> postsMapper;

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static final String POSTS_ENDPOINT = "posts";

    /**
     * Retrieves posts from an external API and saves them to the repository.
     * @return a list of posts retrieved from the external API.
     */
    @Override
    public List<Map<String, Object>> getPosts() {
        String url = BASE_URL + POSTS_ENDPOINT;
        HttpEntity<Void> httpEntity = new HttpEntity<>(getHeaders());
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, List.class);

        saveAll(response);
        return response.getBody();
    }

    /**
     * Converts the JSON response to a list of {@link PostsEntity} objects and saves them to the repository.
     * @param response the response containing the posts data.
     */
    private void saveAll(ResponseEntity<List> response) {
        // Convert the JSON response to a list of PostsEntity objects
        List<PostsEntity> postEntities = ((List<Map<String, Object>>) response.getBody()).stream()
                .map(postsMapper::mapFrom)
                .collect(Collectors.toList());

        thiredAPiRepository.saveAll(postEntities);
    }

    /**
     * Creates HTTP headers for the request.
     * @return the HTTP headers with JSON content type and accept type.
     */
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
