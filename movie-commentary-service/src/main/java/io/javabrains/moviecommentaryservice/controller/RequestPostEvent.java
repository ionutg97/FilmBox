package io.javabrains.moviecommentaryservice.controller;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;

@Component
public class RequestPostEvent {

    @EventListener
    public void handleEvent(RequestHandledEvent e) {
        System.out.println("-- RequestHandledEvent --");
        System.out.println(e);
    }
}
