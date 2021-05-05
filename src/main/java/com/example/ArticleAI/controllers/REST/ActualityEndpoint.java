package com.example.ArticleAI.controllers.REST;

import com.example.ArticleAI.dto.ActualityStatsDto;
import com.example.ArticleAI.dto.ClassDto;
import com.example.ArticleAI.repository.ActualityRepository;
import com.example.ArticleAI.repository.ClassesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ActualityEndpoint {

    private final ClassesRepository classesRepository;
    private final ActualityRepository actualityRepository;

    @GetMapping("/api/actuality")
    public ResponseEntity<Object> getClassActualityByClassId(@RequestParam("classId") Integer classId) {
        final List<ActualityStatsDto> result = actualityRepository.getClassActualityByClassId(classId);

        if (result.size() > 0) {
            return ResponseEntity.ok()
                    .body(result);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/api/classes")
    public ResponseEntity<Object> getAllClasses() {
        final List<ClassDto> result = classesRepository.getAllDto();

        if (result.size() > 0) {
            return ResponseEntity.ok()
                    .body(result);
        }

        return ResponseEntity.notFound().build();
    }
}
