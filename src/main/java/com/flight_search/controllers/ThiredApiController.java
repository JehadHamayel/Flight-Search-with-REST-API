package com.flight_search.controllers;

import com.flight_search.services.impl.ThiredApiServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/thired")
@RequiredArgsConstructor
public class ThiredApiController {
    
    private final ThiredApiServiceImpl thiredApiService;

    @GetMapping("/getPosts")
    public List<Map<String,Object>> getAllPosts() {
        return thiredApiService.getPosts();
    }
}
