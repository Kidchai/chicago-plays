package kidchai.plays.repository.specifications;

import kidchai.plays.model.Event;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecifications {
    public static Specification<Event> priceBetween(int min, int max) {
        return (root, query, criteriaBuilder) -> {
            var minPricePredicate = criteriaBuilder.between(root.get("minPrice"), min, max);
            var maxPricePredicate = criteriaBuilder.between(root.get("maxPrice"), min, max);
            var filterWithinEventPricePredicate = criteriaBuilder.and(
                    criteriaBuilder.lessThanOrEqualTo(root.get("minPrice"), min),
                    criteriaBuilder.greaterThanOrEqualTo(root.get("maxPrice"), max)
            );

            return criteriaBuilder.or(minPricePredicate, maxPricePredicate, filterWithinEventPricePredicate);
        };
    }
}
