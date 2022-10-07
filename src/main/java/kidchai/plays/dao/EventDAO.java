package kidchai.plays.dao;

import kidchai.plays.models.Event;
import kidchai.plays.webscraper.WebScraper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventDAO {
    List<Event> events;

    {
        WebScraper webScraper = new WebScraper();
        events = new ArrayList<>();
        events = webScraper.getEventList();
    }

    public List<Event> index() {
        return events;
    }
}
