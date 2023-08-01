package kidchai.chicago.plays.repository.specifications.eventSpecifications;

import kidchai.chicago.plays.model.Event;
import kidchai.chicago.plays.repository.EventRepository;
import kidchai.chicago.plays.repository.specifications.EventSpecifications;
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
    public void whenMinPrice20MaxPrice30_thenTwoEventsReturned() {
        Specification<Event> spec = EventSpecifications.priceBetween(20, 30);
        List<Event> events = eventRepository.findAll(spec);
        assertEquals(2, events.size());
    }

    @Test
    public void whenMinPrice50MaxPrice50_thenOneEventReturned() {
        Specification<Event> spec = EventSpecifications.priceBetween(50, 50);
        List<Event> events = eventRepository.findAll(spec);
        assertEquals(1, events.size());
    }

    @Test
    public void whenMinPriceNullMaxPriceNull_thenAllEventsReturned() {
        Specification<Event> spec = EventSpecifications.priceBetween(null, null);
        List<Event> events = eventRepository.findAll(spec);
        assertEquals(3, events.size());
    }
}