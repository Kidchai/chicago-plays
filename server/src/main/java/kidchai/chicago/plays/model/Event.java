package kidchai.chicago.plays.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
public class Event {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "events_genres",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres = new ArrayList<>();

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "title")
    private String title;
    @Column(name = "theatre")
    private String theatre;
    @Column(name = "first_date")
    private LocalDate firstDate;
    @Column(name = "last_date")
    private LocalDate lastDate;
    @Column(name = "description", length = 4000)
    private String description;
    @Column(name = "event_url")
    private String eventUrl;
    @Column(name = "min_price")
    private Integer minPrice;
    @Column(name = "max_price")
    private Integer maxPrice;
    @Column(name = "next_show")
    private LocalDateTime nextShow;

    public Event(String title, LocalDate firstDate, LocalDate lastDate, String theatre, String description,
                 String eventUrl, String imageUrl, int minPrice, int maxPrice, LocalDateTime nextShow) {
        this.title = title;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
        this.theatre = theatre;
        this.description = description;
        this.eventUrl = eventUrl;
        this.imageUrl = imageUrl;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.nextShow = nextShow;
    }

    public void setPrice(String price) {
        if (price != null) {
            var prices = price.replace("from ", "").replace("$", "").split(" – ");
            try {
                minPrice = (int) Double.parseDouble(prices[0]);
            } catch (NumberFormatException e) {
                minPrice = null;
            }

            try {
                if (prices.length > 1)
                    maxPrice = (int) Double.parseDouble(prices[1]);
            } catch (NumberFormatException e) {
                maxPrice = null;
            }
        }
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public String getGenres() {
        StringBuilder sb = new StringBuilder();
        for (var genre : genres) {
            sb.append(genre.getGenre()).append(", ");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
}
