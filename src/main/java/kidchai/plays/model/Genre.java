package kidchai.plays.model;

import javax.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {

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
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String eventUrl) {
        this.genre = genre;
    }
}
