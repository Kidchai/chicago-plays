package kidchai.plays.dao;

import kidchai.plays.model.Event;
import kidchai.plays.webscraper.WebScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

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
        jdbcTemplate.update("DELETE FROM genres_events");
        WebScraper webScraper = new WebScraper();
        saveEvents(webScraper.getEventList());
        //saveGenres(webScraper.getGenresSet());
    }

    private void saveEvents(List<Event> events) {
        for (Event event : events) {
            jdbcTemplate.update("INSERT INTO events(title, first_date, last_date, theatre, genres, description, " +
                            "event_url, min_price, max_price, nextshow) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    event.getTitle(), event.getFirstDate(), event.getLastDate(), event.getTheatre(),
                    event.getGenres(), event.getDescription(), event.getEventUrl(), event.getMinPrice(),
                    event.getMaxPrice(), event.getNextShow());

            int eventId = jdbcTemplate.queryForObject("SELECT id FROM events WHERE event_url=?", new Object[]{event.getEventUrl()}, Integer.class);

            String[] genres;
            if (event.getGenres() != null) {
                genres = event.getGenres().split(", ");
                for (String genre : genres) {
                    int genreId = getGenreId(genre);
                    if (genreId == 0) {
                        jdbcTemplate.update("INSERT INTO genres(genre) VALUES(?)", genre);
                    }
                    if (genreId != 0) {
                        jdbcTemplate.update("INSERT INTO genres_events(genre_id, event_id) VALUES(?,?)", genreId, eventId);
                    }
                }
            }
        }
    }

    private int getGenreId(String genre) {
        String sql;
        int id = 0;
        try {
            sql = "SELECT id FROM genres WHERE genre=?";
            id = jdbcTemplate.queryForObject(sql, new Object[]{genre}, Integer.class);
        } catch (Exception ignored) {
        }
        return id;
    }
}
