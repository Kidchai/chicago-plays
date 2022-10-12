package kidchai.plays.controllers;

import kidchai.plays.dao.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventsController {
    private final EventDao eventDAO;

    @Autowired
    public EventsController(EventDao eventDAO) {
        this.eventDAO = eventDAO;
    }

    @GetMapping("/events")
    public String showEvents(Model model) {
        model.addAttribute("events", eventDAO.getEvents());
        return "events/events";
    }
}
