package kidchai.chicago.plays.controllers;

import kidchai.chicago.plays.dtos.FilterDto;
import kidchai.chicago.plays.model.Event;
import kidchai.chicago.plays.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@Controller
@RequestMapping("/api/events")
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

    @ResponseBody
    @GetMapping("")
    public List<Event> getAllEvents(@ModelAttribute FilterDto filter) {
        return eventService.getAllEvents(filter);
    }
}
