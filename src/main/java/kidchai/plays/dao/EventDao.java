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
        session.createQuery("delete from Genre cascade").executeUpdate();
        WebScraper webScraper = new WebScraper(sessionFactory);
        webScraper.saveEvents();
    }
}
