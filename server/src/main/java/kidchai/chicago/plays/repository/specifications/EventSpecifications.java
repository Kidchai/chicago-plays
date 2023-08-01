package kidchai.chicago.plays.repository.specifications;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import kidchai.chicago.plays.model.Event;
import kidchai.chicago.plays.model.Genre;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;

public class EventSpecifications {

    public static Specification<Event> priceBetween(Integer filterMinPrice, Integer filterMaxPrice) {
        return (root, query, criteriaBuilder) -> {
            Predicate lowerBoundPredicate;
            Predicate upperBoundPredicate;

            lowerBoundPredicate = criteriaBuilder.lessThanOrEqualTo(
                    criteriaBuilder.coalesce(root.get("minPrice"), root.get("maxPrice")),
                    filterMaxPrice
            );

            upperBoundPredicate = criteriaBuilder.greaterThanOrEqualTo(
                    criteriaBuilder.coalesce(root.get("maxPrice"), root.get("minPrice")),
                    filterMinPrice
            );

            return criteriaBuilder.and(lowerBoundPredicate, upperBoundPredicate);
        };
    }

    public static Specification<Event> dateBetween(LocalDateTime firstDate, LocalDateTime lastDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("firstDate"), firstDate, lastDate);
    }

    public static Specification<Event> dateFrom(LocalDateTime firstDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.and(
                        criteriaBuilder.lessThanOrEqualTo(root.get("firstDate"), firstDate),
                        criteriaBuilder.greaterThanOrEqualTo(root.get("lastDate"), firstDate)
                ),
                criteriaBuilder.greaterThanOrEqualTo(root.get("firstDate"), firstDate)
        );
    }

    public static Specification<Event> dateTo(LocalDateTime lastDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.and(
                        criteriaBuilder.lessThanOrEqualTo(root.get("firstDate"), lastDate),
                        criteriaBuilder.greaterThanOrEqualTo(root.get("lastDate"), lastDate)
                ),
                criteriaBuilder.lessThanOrEqualTo(root.get("lastDate"), lastDate)
        );
    }

    public static Specification<Event> withGenres(List<Genre> filterGenres) {
        return (root, query, criteriaBuilder) -> {
            if (filterGenres == null || filterGenres.isEmpty())
                return criteriaBuilder.conjunction();

            Join<Event, Genre> genreJoin = root.join("genres");

            return genreJoin.get("genre").in(filterGenres.stream()
                    .map(Genre::getGenre)
                    .toList());
        };
    }
}
