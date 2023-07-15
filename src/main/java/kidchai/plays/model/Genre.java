package kidchai.plays.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genres")
public class Genre {

    @ManyToMany(mappedBy = "genres")
    private List<Event> events;

    @Id
    @Column(name = "genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "genre")
    private String genre;

    public Genre() {
    }

    public Genre(String genre) {
        this.genre = genre;
        this.events = new ArrayList<>();
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String eventUrl) {
        this.genre = genre;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
