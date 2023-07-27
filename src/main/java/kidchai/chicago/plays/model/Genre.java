package kidchai.chicago.plays.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genres")
@Getter
@Setter
@NoArgsConstructor
public class Genre implements Comparable<Genre> {

    @ManyToMany(mappedBy = "genres")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private List<Event> events;

    @Id
    @Column(name = "genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "genre")
    private String genre;

    public Genre(String genre) {
        this.genre = genre;
        this.events = new ArrayList<>();
    }

    @Override
    public int compareTo(Genre o) {
        return this.getGenre().compareTo(o.getGenre());
    }
}