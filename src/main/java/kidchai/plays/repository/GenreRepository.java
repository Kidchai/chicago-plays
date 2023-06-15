package kidchai.plays.repository;

import kidchai.plays.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Genre findByGenre(String genre);
}
