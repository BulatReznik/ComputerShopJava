package ru.ulstu.is.sbapp.speaker.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ulstu.is.sbapp.speaker.domain.Speaker;
import ru.ulstu.is.sbapp.speaker.domain.SpeakerEng;
import ru.ulstu.is.sbapp.speaker.domain.SpeakerRus;

@Configuration
public class SpeakerConfiguration {
    private final Logger log = LoggerFactory.getLogger(SpeakerConfiguration.class);

    @Bean(value = "ru", initMethod = "init", destroyMethod = "destroy")
    public SpeakerRus createRusSpeaker() {
        log.info("Call createRusSpeaker()");
        return new SpeakerRus();
    }

    @Bean(value = "en")
    public Speaker createEngSpeaker() {
        log.info("Call createEngSpeaker()");
        return new SpeakerEng();
    }
}
