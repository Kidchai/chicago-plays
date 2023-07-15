package kidchai.plays.repository;

import jakarta.persistence.criteria.JoinType;
import kidchai.plays.model.Event;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {

    default List<Event> getAllEventsWithGenres(Specification<Event> spec) {
        Specification<Event> specWithFetch = (root, query, criteriaBuilder) -> {
            if (query.getResultType() == Event.class) {
                root.fetch("genres", JoinType.LEFT);
            }

            return spec.toPredicate(root, query, criteriaBuilder);
        };
        return findAll(specWithFetch);
    }
}
