package ru.ulstu.is.sbapp.speaker.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component(value = "de")
public class SpeakerDeu implements Speaker {
    private final Logger log = LoggerFactory.getLogger(SpeakerDeu.class);

    @Override
    public String say() {
        return "Hallo";
    }

    @PostConstruct
    public void init() {
        log.info("SpeakerDeu.init()");
    }

    @PreDestroy
    public void destroy() {
        log.info("SpeakerDeu.destroy()");
    }
}
