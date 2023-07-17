package kidchai.plays.repository.specifications;

import jakarta.persistence.criteria.Predicate;
import kidchai.plays.model.Event;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class EventSpecifications {
    public static Specification<Event> priceBetween(Integer min, Integer max) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (min != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("maxPrice"), min));
            }
            if (max != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("minPrice"), max));
            }

            return predicate;
        };
    }


    public static Specification<Event> dateBetween(LocalDateTime firstDate, LocalDateTime lastDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("firstDate"), firstDate, lastDate);
    }

    public static Specification<Event> dateFrom(LocalDateTime firstDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("firstDate"), firstDate);
    }

    public static Specification<Event> dateTo(LocalDateTime lastDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("lastDate"), lastDate);
    }
}
