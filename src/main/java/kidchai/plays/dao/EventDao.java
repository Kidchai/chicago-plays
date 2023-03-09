package kidchai.plays.dao;

import kidchai.plays.model.Event;
import kidchai.plays.webscraper.WebScraper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class EventDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public EventDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Event> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select e from Event e", Event.class).getResultList();
    }

    @Transactional
    public void refreshEvents() {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from Event cascade").executeUpdate();
        WebScraper webScraper = new WebScraper();
        saveEvents(webScraper.getEventList());
    }

    private void saveEvents(List<Event> events) {
        Session session = sessionFactory.getCurrentSession();
        for (var event : events) {
            Event newEvent = new Event(event.getTitle(), event.getTheatre(), event.getDescription(),
                    event.getEventUrl(), event.getMinPrice(), event.getMaxPrice());
            var eventId = session.save(newEvent);
            System.out.println(event.getTitle() + " event id: " + eventId);

//            String[] genres;
//            if (event.getGenre() != null) {
//                genres = event.getGenre().split(", ");
//                for (var genre : genres) {
//                    var genreId = getGenreId(genre);
//                    if (genreId == 0) {
//                        var INSERT_GENRE = "INSERT INTO genres(genre) VALUES(?)";
//                        var keyHolderGenres = new GeneratedKeyHolder();
//                        jdbcTemplate.update(
//                                connection -> {
//                                    PreparedStatement ps = connection.prepareStatement(INSERT_GENRE, new String[]{"genre_id"});
//                                    ps.setString(1, genre);
//                                    return ps;
//                                },
//                                keyHolderGenres);
//                        genreId = keyHolderGenres.getKey().intValue();
//                    }
//                    if (genreId > 0) {
//                        var INSERT_GENRE_EVENTS = "INSERT INTO genres_events(genre_id, event_id) VALUES(?,?)";
//                        jdbcTemplate.update(INSERT_GENRE_EVENTS, genreId, eventId);
//                    }
//                }
//            }
        }
    }

//    private int getGenreId(String genre) {
//        if (genre.isEmpty()) {
//            return -1;
//        }
//        var id = 0;
//        try {
//            Session session = sessionFactory.getCurrentSession();
//            id = (Integer) session.createQuery("select id from Genre where genre=:genre")
//                    .setParameter("genre", genre)
//                    .uniqueResult();
//        } catch (Exception ignored) {
//        }
//        return id;
//    }
}
