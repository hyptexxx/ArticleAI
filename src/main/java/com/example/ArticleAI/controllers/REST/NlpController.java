package com.example.ArticleAI.controllers.REST;

import com.example.ArticleAI.mappers.YakeMapper;
import com.example.ArticleAI.models.NlpResponse;
import com.example.ArticleAI.modules.nlpFilter.service.NlpFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NlpController {

    private final NlpFilterService nlpFilterService;

    @PostMapping(value = "/api/nlp/analyse")
    public ResponseEntity<Object> actualityAnalyse(@RequestParam("yakeData") String yakeData) throws ParseException {
        List<NlpResponse> filteredYake = nlpFilterService.doFilter(YakeMapper
                .parse(yakeData)
                .orElseThrow(() -> new ParseException("unparsable yake data", 0)));

        if (filteredYake.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(filteredYake);
    }
}
