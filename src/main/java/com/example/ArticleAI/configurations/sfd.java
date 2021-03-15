package com.example.ArticleAI.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class sfd {
    @Autowired
    public SimpMessageSendingOperations messagingTemplate;

    @SendTo("/topic/analyseSteps")
    @MessageMapping("/topic/analyseSteps")
    public String message(String message) {
        return message;
    }
}
