package my.books.controllers;

import my.books.DAO.GenreDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/genres")
public class GenreController {

    private final GenreDAO genreDAO;

    public GenreController(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    @GetMapping
    public String allGenres(Model model) {
        model.addAttribute("genres", genreDAO.findAll());
        return "genres/allGenres";
    }

    @GetMapping("{g_title}")
    public String aGenre(@PathVariable("g_title") String g_title, Model model) {
        model.addAttribute("a_genre", genreDAO.findByTitle(g_title));
        model.addAttribute("genreBooks", genreDAO.genreBooks(g_title));
        return "genres/aGenre";
    }
}
