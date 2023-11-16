package kidchai.chicago.plays.controllers;

import kidchai.chicago.plays.dtos.FilterDto;
import kidchai.chicago.plays.model.Genre;
import kidchai.chicago.plays.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@Controller
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @ResponseBody
    @GetMapping()
    public List<Genre> getAllGenres(@ModelAttribute FilterDto filter) {
        var genres = genreRepository.findAll();
        Collections.sort(genres);
        return genres;
    }
}