package kidchai.plays.repository.specifications.eventSpecifications;

import kidchai.plays.model.Genre;
import kidchai.plays.repository.EventRepository;
import kidchai.plays.repository.specifications.EventSpecifications;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GenresSpecificationsTest {

    @Autowired
    private EventRepository eventRepository;

    private static Genre comedy;
    private static Genre musical;
    private static Genre drama;

    @BeforeAll
    public static void setUp() {
        comedy = new Genre("Comedy");
        comedy.setId(1);
        musical = new Genre("Musical");
        musical.setId(2);
        drama = new Genre("Drama");
        drama.setId(3);
    }

    @AfterAll
    public static void tearDown() {
        comedy = null;
        musical = null;
    }

    @Test
    @DisplayName("Returns 2 events with genre 'Musical'")
    public void whenGenreComedy_thenTwoEventsReturned() {
        var spec = EventSpecifications.withGenres(Collections.singletonList(musical));
        var events = eventRepository.findAll(spec);
        assertEquals(2, events.size());
    }

    @Test
    @DisplayName("Returns 2 events with genre 'Comedy' and 'Drama'")
    public void whenGenreComedyAndDrama_thenTwoEventsReturned() {
        var spec = EventSpecifications.withGenres(List.of(comedy, drama));
        var events = eventRepository.findAll(spec);
        assertEquals(2, events.size());
    }

    @Test
    @DisplayName("Returns 3 events with genre null")
    public void whenGenreNull_thenThreeEventsReturned() {
        var spec = EventSpecifications.withGenres(null);
        var events = eventRepository.findAll(spec);
        assertEquals(3, events.size());
    }
}
