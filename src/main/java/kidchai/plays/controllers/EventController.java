package kidchai.plays.controllers;

import kidchai.plays.dtos.FilterDto;
import kidchai.plays.model.Event;
import kidchai.plays.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/refresh")
    public String refreshEvents() {
        eventService.refreshEvents();
        return "redirect:/events";
    }

    @GetMapping("")
    public String getAllEvents(@ModelAttribute FilterDto filter, Model model) {
        List<Event> events = eventService.getAllEvents(filter);
        model.addAttribute("events", eventService.getAllEvents(filter));
        model.addAttribute("filter", filter);
        return "index";
    }
}
