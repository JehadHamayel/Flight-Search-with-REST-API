package com.flight_search.services;

import java.util.List;
import java.util.Map;

/**
 * Service interface for interacting with a third-party API to retrieve posts.
 */
public interface ThiredApiService {

    /**
     * Retrieves a list of posts from a third-party API.
     * @return a list of posts, where each post is represented as a map of key-value pairs.
     */
    List<Map<String, Object>> getPosts();
}
