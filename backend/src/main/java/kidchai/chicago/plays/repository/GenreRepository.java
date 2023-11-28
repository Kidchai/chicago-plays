package kidchai.chicago.plays.repository;

import kidchai.chicago.plays.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Genre findByGenre(String genre);
}
