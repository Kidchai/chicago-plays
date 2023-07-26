package kidchai.chicago.plays.service;

import jakarta.transaction.Transactional;
import kidchai.chicago.plays.dtos.FilterDto;
import kidchai.chicago.plays.model.Event;
import kidchai.chicago.plays.repository.EventRepository;
import kidchai.chicago.plays.repository.GenreRepository;
import kidchai.chicago.plays.repository.specifications.EventSpecifications;
import kidchai.chicago.plays.webscraper.WebScraper;
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
        var spec = Specification
                .where(EventSpecifications.priceBetween(filter.getMinPrice(), filter.getMaxPrice()))
                .and(EventSpecifications.withGenres(filter.getSelectedGenres()));

        if (filter.getFirstDate() != null && filter.getLastDate() != null) {
            spec = spec.and(EventSpecifications.dateBetween(filter.getFirstDateTime(), filter.getLastDateTime()));
        } else if (filter.getFirstDate() != null) {
            spec = spec.and(EventSpecifications.dateFrom(filter.getFirstDateTime()));
        } else if (filter.getLastDate() != null) {
            spec = spec.and(EventSpecifications.dateTo(filter.getLastDateTime()));
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
