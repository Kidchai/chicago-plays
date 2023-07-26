package kidchai.chicago.plays.dtos;

import kidchai.chicago.plays.model.Genre;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class FilterDto {

    private Integer minPrice;
    private Integer maxPrice;
    private LocalDate firstDate;
    private LocalDate lastDate;

    private List<Genre> selectedGenres;

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
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

    public LocalDateTime getFirstDateTime() {
        return firstDate.atTime(LocalTime.MIN);
    }

    public LocalDateTime getLastDateTime() {
        return lastDate.atTime(LocalTime.MAX);
    }

    public List<Genre> getSelectedGenres() {
        return selectedGenres;
    }

    public void setSelectedGenres(List<Genre> selectedGenres) {
        this.selectedGenres = selectedGenres;
    }
}