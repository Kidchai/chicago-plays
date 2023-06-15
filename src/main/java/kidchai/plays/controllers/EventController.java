package kidchai.plays.controllers;

import kidchai.plays.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "redirect:index";
    }

    @GetMapping("/index")
    public String getAllEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "index";
    }
}
