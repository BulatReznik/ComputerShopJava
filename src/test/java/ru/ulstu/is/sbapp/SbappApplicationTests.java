package ru.ulstu.is.sbapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.speaker.service.SpeakerService;

@SpringBootTest
class SbappApplicationTests {
    @Autowired
    SpeakerService speakerService;

    @Test
    void testSpeakerRus() {
        final String res = speakerService.say("Мир", "ru");
        Assertions.assertEquals("Привет Мир!", res);
    }

    @Test
    void testSpeakerEng() {
        final String res = speakerService.say("World", "en");
        Assertions.assertEquals("Hello World!", res);
    }

    @Test
    void testSpeakerDeu() {
        final String res = speakerService.say("Welt", "de");
        Assertions.assertEquals("Hallo Welt!", res);
    }

    @Test
    void testSpeakerErrorWired() {
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> speakerService.say("Мир", "rus"));
    }
}
