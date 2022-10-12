package kidchai.plays.dao;

import kidchai.plays.models.Event;
import kidchai.plays.webscraper.WebScraper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventDao {
    private List<Event> events;

    public List<Event> getEvents() {
        WebScraper webScraper = new WebScraper();
        events = new ArrayList<>();
        events = webScraper.getEventList();

        return events;
    }
}
