package kidchai.plays.repository.specifications;

import jakarta.persistence.criteria.Predicate;
import kidchai.plays.model.Event;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class EventSpecifications {

    public static Specification<Event> priceBetween(Integer minPrice, Integer maxPrice) {
        return (root, query, criteriaBuilder) -> {
            Predicate minPricePredicate;
            Predicate maxPricePredicate;

            if (minPrice == null && maxPrice == null) {
                return criteriaBuilder.conjunction();
            } else if (minPrice == null) {
                minPricePredicate = criteriaBuilder.conjunction();
                maxPricePredicate = criteriaBuilder.or(
                        criteriaBuilder.lessThanOrEqualTo(root.get("minPrice"), maxPrice),
                        criteriaBuilder.lessThanOrEqualTo(root.get("maxPrice"), maxPrice)
                );
            } else if (maxPrice == null) {
                maxPricePredicate = criteriaBuilder.conjunction();
                minPricePredicate = criteriaBuilder.or(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("minPrice"), minPrice),
                        criteriaBuilder.lessThanOrEqualTo(root.get("maxPrice"), minPrice)
                );
            } else {
                Predicate lowerBoundPredicate = criteriaBuilder.or(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("minPrice"), minPrice),
                        criteriaBuilder.lessThanOrEqualTo(root.get("maxPrice"), minPrice)
                );
                Predicate upperBoundPredicate = criteriaBuilder.or(
                        criteriaBuilder.lessThanOrEqualTo(root.get("minPrice"), maxPrice),
                        criteriaBuilder.greaterThanOrEqualTo(root.get("maxPrice"), maxPrice)
                );

                return criteriaBuilder.and(lowerBoundPredicate, upperBoundPredicate);
            }

            return criteriaBuilder.and(minPricePredicate, maxPricePredicate);
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