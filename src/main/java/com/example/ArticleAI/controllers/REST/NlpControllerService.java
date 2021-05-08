package com.example.ArticleAI.controllers.REST;

import com.example.ArticleAI.models.*;
import com.example.ArticleAI.modules.distanceService.DistanceService;
import com.example.ArticleAI.modules.nlpFilterService.NlpFilterService;
import com.example.ArticleAI.repository.ClassesRepository;
import com.example.ArticleAI.util.RecomendationUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NlpControllerService {
    private final NlpFilterService nlpFilterService;
    private final RecomendationUtilService recomendationUtilService;
    private final DistanceService distanceService;
    private final SimpMessageSendingOperations messagingTemplate;

    @SendToUser("/topic/analyseSteps")
    public List<ClassDistance> getDistance(List<NlpResponse> filteredKeywords) {
        final String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        final List<String> keywordNames;

        messagingTemplate.convertAndSendToUser(sessionId, "/topic/analyseSteps", "4");

        if (filteredKeywords.isEmpty()) {
            return null;
        }

        keywordNames = RecomendationUtilService.getKeywordNams(filteredKeywords);

        if (filteredKeywords.isEmpty()) {
            return null;
        }

        return distanceService.getDistance(keywordNames, recomendationUtilService.getKeyWordClass());
    }

    public List<NlpResponse> doFilter(List<YakeResponse> yakeData) throws ParseException {
        final List<NlpResponse> filteredYake = nlpFilterService.doFilter(yakeData);
        final List<NlpResponse> filteredKeywords = filteredYake.stream()
                .filter(keyWord -> keyWord.getIsGood() == 1)
                .collect(Collectors.toList());

        if (filteredKeywords.isEmpty()) {
            return null;
        }

        return filteredKeywords;
    }
}
