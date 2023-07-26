package kidchai.chicago.plays.controllers;

import kidchai.chicago.plays.dtos.FilterDto;
import kidchai.chicago.plays.model.Event;
import kidchai.chicago.plays.model.Genre;
import kidchai.chicago.plays.repository.GenreRepository;
import kidchai.chicago.plays.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/api/events")
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

    @ResponseBody
    @GetMapping("")
    public List<Event> getAllEvents(@ModelAttribute FilterDto filter) {
        return eventService.getAllEvents(filter);
    }

    @ResponseBody
    @GetMapping("/genres")
    public List<Genre> getAllGenres(@ModelAttribute FilterDto filter) {
        var genres = genreRepository.findAll();
        Collections.sort(genres);
        return genres;
    }
}
