package kidchai.plays.repository.specifications.eventSpecifications;

import kidchai.plays.model.Event;
import kidchai.plays.repository.EventRepository;
import kidchai.plays.repository.specifications.EventSpecifications;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PriceSpecificationsTest {
    @Autowired
    private EventRepository eventRepository;

    @Test
    @DisplayName("Returns 2 events with price >= minPrice and price <= maxPrice")
    public void whenMinPrice20MaxPrice30_thenOneEventReturned() {
        Specification<Event> spec = EventSpecifications.priceBetween(20, 30);
        List<Event> events = eventRepository.findAll(spec);
        assertEquals(2, events.size());
    }

    @Test
    @DisplayName("Returns 2 events with price >= minPrice")
    public void whenMinPrice20MaxPriceNull_thenTwoEventsReturned() {
        Specification<Event> spec = EventSpecifications.priceBetween(20, null);
        List<Event> events = eventRepository.findAll(spec);
        assertEquals(2, events.size());
    }

    @Test
    @DisplayName("Returns 2 events with price <= maxPrice")
    public void whenMinPriceNullMaxPrice30_thenTwoEventReturned() {
        Specification<Event> spec = EventSpecifications.priceBetween(null, 30);
        List<Event> events = eventRepository.findAll(spec);
        assertEquals(2, events.size());
    }
}