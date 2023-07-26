package kidchai.chicago.plays.dtos;

import kidchai.chicago.plays.model.Genre;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class FilterDto {

    private Integer minPrice;
    private Integer maxPrice;
    private LocalDate firstDate;
    private LocalDate lastDate;
    private List<Genre> selectedGenres;

    public LocalDateTime getFirstDateTime() {
        return firstDate.atTime(LocalTime.MIN);
    }
    public LocalDateTime getLastDateTime() {
        return lastDate.atTime(LocalTime.MAX);
    }
}