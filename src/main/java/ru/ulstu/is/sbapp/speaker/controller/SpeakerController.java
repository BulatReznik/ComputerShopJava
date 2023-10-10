package ru.ulstu.is.sbapp.speaker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ulstu.is.sbapp.WebConfiguration;
import ru.ulstu.is.sbapp.speaker.service.SpeakerService;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/speaker")
public class SpeakerController {
    private final SpeakerService speakerService;

    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @GetMapping
    public String hello(@RequestParam(value = "name", defaultValue = "Мир") String name,
                        @RequestParam(value = "lang", defaultValue = "ru") String lang) {
        return speakerService.say(name, lang);
    }
}
