package kidchai.plays.dao;

import kidchai.plays.model.Event;
import kidchai.plays.webscraper.WebScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class EventDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EventDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Event> index() {
        return jdbcTemplate.query("SELECT * FROM events", new BeanPropertyRowMapper<>(Event.class));
    }

    public void refreshEvents() {
        jdbcTemplate.update("DELETE FROM events");
        jdbcTemplate.update("DELETE FROM genres");
        WebScraper webScraper = new WebScraper();
        saveEvents(webScraper.getEventList());
        saveGenres(webScraper.getGenresSet());
    }

    private void saveEvents(List<Event> events) {
        for (Event event : events) {
            jdbcTemplate.update("INSERT INTO events(title, firstDate, lastDate, theatre, genres, description, " +
                            "eventurl, minprice, maxprice, nextshow) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    event.getTitle(), event.getFirstDate(), event.getLastDate(), event.getTheatre(),
                    event.getGenres(), event.getDescription(), event.getEventUrl(), event.getMinPrice(),
                    event.getMaxPrice(), event.getNextShow());
        }
    }

    private void saveGenres(Set<String> genresSet) {
        for (String genre : genresSet) {
            jdbcTemplate.update("INSERT INTO genres(genre) VALUES(?)", genre);
        }
    }
}
