package com.example.ArticleAI.controllers.REST;

import com.example.ArticleAI.models.KeywordClass;
import com.example.ArticleAI.models.NlpResponse;
import com.example.ArticleAI.models.Recomendation;
import com.example.ArticleAI.modules.distanceService.DistanceService;
import com.example.ArticleAI.modules.nlpFilterService.NlpFilterService;
import com.example.ArticleAI.parser.YakeResponseParser;
import com.example.ArticleAI.repository.ClassesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NlpController {

    private final NlpFilterService nlpFilterService;
    private final ClassesRepository classesRepository;
    private final DistanceService distanceService;
    private final SimpMessageSendingOperations messagingTemplate;

    @PostMapping(value = "/api/nlp/analyse")
    @SendToUser("/topic/analyseSteps")
    public ResponseEntity<Object> actualityAnalyse(@RequestParam("yakeData") String yakeData) throws ParseException {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        List<NlpResponse> filteredYake = nlpFilterService.doFilter(YakeResponseParser
                .parse(yakeData)
                .orElseThrow(() -> new ParseException("unparsable yake data", 0)));

        List<KeywordClass> classes = classesRepository.getAllClassesEmbeddings();

        messagingTemplate.convertAndSendToUser(sessionId, "/topic/analyseSteps", "5");
jkl
        classes = classes.stream()
                .map(clazz -> KeywordClass.builder()
                        .classActuality(classesRepository.getActualityByClassName(clazz.getName())
                                .orElseThrow(IllegalAccessError::new))
                        .name(clazz.getName())
                        .embedding(clazz.getEmbedding())
                        .build())
                .collect(Collectors.toList());

        messagingTemplate.convertAndSendToUser(sessionId, "/topic/analyseSteps", "6");

        Recomendation recomendation = distanceService.getDistance(filteredYake.stream()
                .filter(keyWord -> keyWord.getIsGood() == 1)
                .map(NlpResponse::getNgram)
                .collect(Collectors.toList()), classes, filteredYake);

        if (filteredYake.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(recomendation);
    }
}
