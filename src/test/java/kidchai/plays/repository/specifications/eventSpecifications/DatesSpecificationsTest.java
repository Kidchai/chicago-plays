package kidchai.plays.repository.specifications.eventSpecifications;

import kidchai.plays.repository.EventRepository;
import kidchai.plays.repository.specifications.EventSpecifications;
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
    @DisplayName("Returns 2 events with date >= minDate and date <= maxDate")
    public void whenMinDate2023_07_21MaxDate2023_07_24_thenTwoEventsReturned() {
        var minDate = LocalDate.parse("2023-07-21").atTime(LocalTime.MIN);
        var maxDate = LocalDate.parse("2023-07-24").atTime(LocalTime.MAX);
        var spec = EventSpecifications.dateBetween(minDate, maxDate);
        var events = eventRepository.findAll(spec);
        assertEquals(2, events.size());
    }

    @Test
    @DisplayName("Returns 2 events with date >= minDate")
    public void whenMinDate2023_07_26MaxDateNull_thenTwoEventsReturned() {
        var minDate = LocalDate.parse("2023-07-26").atTime(LocalTime.MIN);
        var spec = EventSpecifications.dateFrom(minDate);
        var events = eventRepository.findAll(spec);
        assertEquals(2, events.size());
    }

    @Test
    @DisplayName("Returns 2 events with date <= maxDate")
    public void whenMinDateNullMaxDate2023_07_23_thenTwoEventsReturned() {
        var maxDate = LocalDate.parse("2023-07-23").atTime(LocalTime.MAX);
        var spec = EventSpecifications.dateTo(maxDate);
        var events = eventRepository.findAll(spec);
        assertEquals(2, events.size());
    }
}
