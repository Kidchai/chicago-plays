package kidchai.chicago.plays.repository.specifications.eventSpecifications;

import kidchai.chicago.plays.repository.EventRepository;
import kidchai.chicago.plays.repository.specifications.EventSpecifications;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class DatesSpecificationsTest {
    @Autowired
    private EventRepository eventRepository;

    @Test
    public void whenFirstDate2023_07_21LastDate2023_07_24_thenThreeEventsReturned() {
        var firstDate = LocalDate.parse("2023-07-21").atTime(LocalTime.MIN);
        var lastDate = LocalDate.parse("2023-07-24").atTime(LocalTime.MAX);
        var spec = EventSpecifications.dateBetween(firstDate, lastDate);
        var events = eventRepository.findAll(spec);
        assertEquals(3, events.size());
    }

    @Test
    public void whenFirstDate2023_07_26LastDateNull_thenThreeEventsReturned() {
        var firstDate = LocalDate.parse("2023-07-26").atTime(LocalTime.MIN);
        var spec = EventSpecifications.dateFrom(firstDate);
        var events = eventRepository.findAll(spec);
        assertEquals(3, events.size());
    }

    @Test
    public void whenFirstDateNullLastDate2023_07_23_thenThreeEventsReturned() {
        var lastDate = LocalDate.parse("2023-07-23").atTime(LocalTime.MAX);
        var spec = EventSpecifications.dateTo(lastDate);
        var events = eventRepository.findAll(spec);
        assertEquals(3, events.size());
    }
}
