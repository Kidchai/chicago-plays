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

    private Integer min_price;
    private Integer max_price;
    private LocalDate first_date;
    private LocalDate last_date;
    private List<Genre> genres;
    private String search;

    public LocalDateTime getFirstDateTime() {
        return first_date.atTime(LocalTime.MIN);
    }
    public LocalDateTime getLastDateTime() {
        return last_date.atTime(LocalTime.MAX);
    }
}