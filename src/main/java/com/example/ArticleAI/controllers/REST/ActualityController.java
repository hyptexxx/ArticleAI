package com.example.ArticleAI.controllers.REST;

import com.example.ArticleAI.modules.actualityResolver.models.Actuality;
import com.example.ArticleAI.modules.actualityResolver.parser.ClassParser;
import com.example.ArticleAI.modules.actualityResolver.service.interfaces.Actuality.IActualityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ActualityController {

    private final
    IActualityService actualityService;


//    @PostMapping(value = "/api/actuality/analyse")
//    public ResponseEntity<Object> actualityAnalyse(@RequestParam("classes") String classes) {
//        List<Actuality> actualityList =  actualityService.getActuality(new ClassParser().getClassesFromJSON(classes));
//        if (!actualityList.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.OK).body(actualityList);
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }
}
