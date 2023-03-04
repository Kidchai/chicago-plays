package kidchai.plays.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Event {
    private String imageUrl;
    private String title;
    private String theatre;
    private String genres;
    private LocalDate firstDate;
    private LocalDate lastDate;
    private String runs;
    private String description;
    private String eventUrl;
    private int minPrice;
    private int maxPrice;
    private String nextShow;

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

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public void setNextShow(String nextShow) {
        this.nextShow = nextShow;
    }

    public String getNextShow() {
        return nextShow;
    }
}
