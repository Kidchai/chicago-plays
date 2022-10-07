package kidchai.plays.controllers;

import kidchai.plays.dao.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventsController {
    private final EventDAO eventDAO;

    @Autowired
    public EventsController(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @GetMapping("/events")
    public String showEvents(Model model) {
        model.addAttribute("events", eventDAO.index());
        return "events/index";
    }
}
