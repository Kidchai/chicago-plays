package kidchai.plays.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Event {
    private String imageUrl;
    private String title;
    private String theatre;
    private String genres;
    private LocalDateTime firstDate;
    private LocalDateTime lastDate;
    private String description;
    private String eventUrl;
    private int minPrice;
    private int maxPrice;
    private LocalDateTime nextShow;

    public Event() {
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

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres == null ? "" : genres;
    }

    public LocalDateTime getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(LocalDateTime firstDate) {
        this.firstDate = firstDate;
    }

    public LocalDateTime getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDateTime lastDate) {
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
            minPrice = (int) Double.parseDouble(prices[0]);
            if (prices.length > 1) {
                maxPrice = (int) Double.parseDouble(prices[1]);
            }
        }
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
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
        if (minPrice == 0 && maxPrice == 0) {
            result = "";
        } else if (maxPrice == 0) {
            result = String.format("from $%d", minPrice);
        } else {
            result = String.format("$%d - $%d", minPrice, maxPrice);
        }
        return result;
    }
}
