package kidchai.plays.repository;

import kidchai.plays.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query("select distinct e from Event e left join fetch e.genres")
    List<Event> getAllEventsWithGenres();
}
