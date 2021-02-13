package com.example.ArticleAI.controllers.REST;

import com.example.ArticleAI.models.YakeResponse;
import com.example.ArticleAI.repository.KmeansRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KmeansOutputEndpoint {

    private final KmeansRepository kmeansRepository;

    @GetMapping(value = "/api/v1/kmeans/yake")
    ResponseEntity<Object> processFiles() {
        Optional<List<YakeResponse>> result = kmeansRepository.getAllKeywords();
        if (result.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
