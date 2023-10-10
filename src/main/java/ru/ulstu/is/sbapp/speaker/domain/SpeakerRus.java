package ru.ulstu.is.sbapp.speaker.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpeakerRus implements Speaker {
    private final Logger log = LoggerFactory.getLogger(SpeakerRus.class);

    @Override
    public String say() {
        return "Привет";
    }

    public void init() {
        log.info("SpeakerRus.init()");
    }

    public void destroy() {
        log.info("SpeakerRus.destroy()");
    }
}
