package kidchai.plays.model;

public class Event {
    private String imageUrl;
    private String title;
    private String theatre;
    private String genres;

    private String runs;
//    private String earliestDate;
//    private String latestDate;
    private String description;
    private String eventUrl;
    private String price;
    private String nextShow;

//    public void setEarliestDate(String earliestDate) {
//        this.earliestDate = earliestDate;
//    }
//
//    public void setLatestDate(String latestDate) {
//        this.latestDate = latestDate;
//    }

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
        this.genres = genres == null ? "": genres;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? "": price;
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
