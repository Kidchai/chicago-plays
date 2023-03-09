package kidchai.plays.controllers;

import kidchai.plays.dao.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/events")
public class EventsController {
    private final EventDao eventDAO;

    @Autowired
    public EventsController(EventDao eventDAO) {
        this.eventDAO = eventDAO;
    }

    @PostMapping("/refresh")
    public String create() {
        eventDAO.refreshEvents();
        return "redirect:/events/index";
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("events", eventDAO.index());
        return "events/index";
    }
}