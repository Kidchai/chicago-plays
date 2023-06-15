package kidchai.plays.service;

import jakarta.transaction.Transactional;
import kidchai.plays.model.Event;
import kidchai.plays.repository.EventRepository;
import kidchai.plays.webscraper.WebScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final WebScraper webScraper;

    @Autowired
    public EventService(EventRepository eventRepository, WebScraper webScraper) {
        this.eventRepository = eventRepository;
        this.webScraper = webScraper;
    }

    public List<Event> getAllEvents() {
        return eventRepository.getAllEventsWithGenres();
    }

    @Transactional
    public void refreshEvents() {
        eventRepository.deleteAllInBatch();
        webScraper.saveEvents();
    }
}
