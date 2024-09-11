package com.flight_search.controllers;

import com.flight_search.services.impl.ThiredApiServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Controller for interacting with the third-party API.
 */
@RestController
@RequestMapping("/api/v1/thired")
@RequiredArgsConstructor
public class ThirdApiController {

    private final ThiredApiServiceImpl thiredApiService;

    /**
     * Retrieves all posts from the third-party API.
     *
     * @return a {@link List} of {@link Map} where each map represents a post
     *         with string keys and object values. The structure of the map depends on the third-party API response.
     */
    @GetMapping("/getPosts")
    public List<Map<String, Object>> getAllPosts() {
        return thiredApiService.getPosts();
    }
}
