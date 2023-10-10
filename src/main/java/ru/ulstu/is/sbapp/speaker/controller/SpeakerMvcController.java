package ru.ulstu.is.sbapp.speaker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ulstu.is.sbapp.speaker.service.SpeakerService;

import java.util.List;

@Controller
@RequestMapping("/speaker")
public class SpeakerMvcController {
    private final SpeakerService speakerService;
    private final List<String> langs;

    public SpeakerMvcController(SpeakerService speakerService) {
        this.speakerService = speakerService;
        this.langs = List.of("ru", "en", "de", "deu");
    }

    @GetMapping
    public String hello(@RequestParam(value = "name", defaultValue = "Мир") String name,
                        @RequestParam(value = "lang", defaultValue = "ru") String lang,
                        Model model) {
        model.addAttribute("langs", langs);
        model.addAttribute("name", name);
        model.addAttribute("lang", lang);
        model.addAttribute("say", speakerService.say(name, lang));
        return "speaker";
    }
}
