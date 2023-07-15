package kidchai.plays.service;

import jakarta.transaction.Transactional;
import kidchai.plays.dtos.FilterDto;
import kidchai.plays.model.Event;
import kidchai.plays.repository.EventRepository;
import kidchai.plays.repository.GenreRepository;
import kidchai.plays.repository.specifications.EventSpecifications;
import kidchai.plays.webscraper.WebScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final WebScraper webScraper;
    private final GenreRepository genreRepository;

    @Autowired
    public EventService(EventRepository eventRepository, WebScraper webScraper,
                        GenreRepository genreRepository) {
        this.eventRepository = eventRepository;
        this.webScraper = webScraper;
        this.genreRepository = genreRepository;
    }

    public List<Event> getAllEvents(FilterDto filter) {
        Specification<Event> spec = Specification.where(null);
        if (filter.getMinPrice() != 0 && filter.getMaxPrice() != 0) {
            spec = spec.and(EventSpecifications.priceBetween(filter.getMinPrice(), filter.getMaxPrice()));
        }

        return eventRepository.getAllEventsWithGenres(spec);
    }

    @Transactional
    public void refreshEvents() {
        eventRepository.deleteAllInBatch();
        genreRepository.deleteAllInBatch();
        webScraper.saveEvents();
    }
}
