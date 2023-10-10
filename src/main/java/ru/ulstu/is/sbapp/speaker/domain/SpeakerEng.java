package ru.ulstu.is.sbapp.speaker.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class SpeakerEng implements Speaker, InitializingBean, DisposableBean {
    private final Logger log = LoggerFactory.getLogger(SpeakerEng.class);

    @Override
    public String say() {
        return "Hello";
    }

    @Override
    public void afterPropertiesSet() {
        log.info("SpeakerEng.afterPropertiesSet()");
    }

    @Override
    public void destroy() {
        log.info("SpeakerEng.destroy()");

    }
}
