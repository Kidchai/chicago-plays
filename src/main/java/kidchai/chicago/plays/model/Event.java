package kidchai.chicago.plays.model;

import jakarta.persistence.*;

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

    public Event() {
    }

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

    public int getId() {
        return id;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(LocalDate firstDate) {
        this.firstDate = firstDate;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheatre() {
        return theatre;
    }

    public void setTheatre(String theatre) {
        this.theatre = theatre;
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

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setNextShow(LocalDateTime nextShow) {
        this.nextShow = nextShow;
    }

    public LocalDateTime getNextShow() {
        return nextShow;
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

    public String getFormattedNextShow() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("h:mma EEE, MMM dd, yyyy")
                .toFormatter(Locale.US);
        return formatter.format(getNextShow());
    }

    public String getFormattedRuns() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("MMM dd")
                .toFormatter(Locale.US);
        String first;
        try {
            first = formatter.format(firstDate);
        } catch (DateTimeParseException e) {
            formatter = new DateTimeFormatterBuilder()
                    .appendPattern("MMM dd, yyyy")
                    .toFormatter(Locale.US);
            first = formatter.format(firstDate);
        }
        String last = lastDate == null ? "" : formatter.format(lastDate);
        return String.format("%s – %s", first, last);
    }

    public String getFormattedPrice() {
        String result;
        if (minPrice == null && maxPrice == null) {
            result = "";
        } else if (maxPrice == null) {
            result = String.format("from $%d", minPrice);
        } else {
            result = String.format("$%d - $%d", minPrice, maxPrice);
        }
        return result;
    }
}
