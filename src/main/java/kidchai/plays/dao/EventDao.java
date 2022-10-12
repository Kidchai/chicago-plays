package kidchai.plays.dao;

import kidchai.plays.model.Event;
import kidchai.plays.webscraper.WebScraper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventDao {

    public List<Event> getEvents() {
        WebScraper webScraper = new WebScraper();
        List<Event> events = new ArrayList<>();
        events = webScraper.getEventList();

        return events;
    }
}
