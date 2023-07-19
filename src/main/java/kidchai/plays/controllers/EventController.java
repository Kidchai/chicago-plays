package kidchai.plays.controllers;

import kidchai.plays.dtos.FilterDto;
import kidchai.plays.repository.GenreRepository;
import kidchai.plays.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final GenreRepository genreRepository;

    @Autowired
    public EventController(EventService eventService,
                           GenreRepository genreRepository) {
        this.eventService = eventService;
        this.genreRepository = genreRepository;
    }

    @PostMapping("/refresh")
    public String refreshEvents() {
        eventService.refreshEvents();
        return "redirect:/events";
    }

    @GetMapping("")
    public String getAllEvents(@ModelAttribute FilterDto filter, Model model) {
        model.addAttribute("events", eventService.getAllEvents(filter));
        model.addAttribute("filter", filter);
        model.addAttribute("genres", genreRepository.findAll());
        return "index";
    }
}
