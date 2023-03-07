package kidchai.plays.dao;

import kidchai.plays.model.Event;
import kidchai.plays.webscraper.WebScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
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
    }

    private void saveEvents(List<Event> events) {
        for (var event : events) {
            var INSERT_EVENT = "INSERT INTO events(title, first_date, last_date, theatre, description, event_url, " +
                    "min_price, max_price, nextshow) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            var keyHolderEvents = new GeneratedKeyHolder();
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(INSERT_EVENT, new String[]{"id"});
                        ps.setString(1, event.getTitle());
                        ps.setTimestamp(2, Timestamp.valueOf(event.getFirstDate()));
                        ps.setTimestamp(3, Timestamp.valueOf(event.getLastDate()));
                        ps.setString(4, event.getTheatre());
                        ps.setString(5, event.getDescription());
                        ps.setString(6, event.getEventUrl());
                        ps.setInt(7, event.getMinPrice());
                        ps.setInt(8, event.getMaxPrice());
                        ps.setTimestamp(9, Timestamp.valueOf(event.getNextShow()));
                        return ps;
                    },
                    keyHolderEvents);
            var eventId = keyHolderEvents.getKey().intValue();

            String[] genres;
            if (event.getGenres() != null) {
                genres = event.getGenres().split(", ");
                for (var genre : genres) {
                    var genreId = getGenreId(genre);
                    if (genreId == 0) {
                        var INSERT_GENRE = "INSERT INTO genres(genre) VALUES(?)";
                        var keyHolderGenres = new GeneratedKeyHolder();
                        jdbcTemplate.update(
                                connection -> {
                                    PreparedStatement ps = connection.prepareStatement(INSERT_GENRE, new String[]{"id"});
                                    ps.setString(1, genre);
                                    return ps;
                                },
                                keyHolderGenres);
                        genreId = keyHolderGenres.getKey().intValue();
                    }
                    if (genreId > 0) {
                        var INSERT_GENRE_EVENTS = "INSERT INTO genres_events(genre_id, event_id) VALUES(?,?)";
                        jdbcTemplate.update(INSERT_GENRE_EVENTS, genreId, eventId);
                    }
                }
            }
        }
    }

    private int getGenreId(String genre) {
        if (genre.isEmpty()) {
            return -1;
        }
        var GET_ID = "SELECT id FROM genres WHERE genre=?";
        int id = 0;
        try {
            id = jdbcTemplate.queryForObject(GET_ID, new Object[]{genre}, Integer.class);
        } catch (Exception ignored) {
        }
        return id;
    }
}
