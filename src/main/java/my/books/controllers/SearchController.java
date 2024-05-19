package my.books.controllers;

import my.books.DAO.SearchDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
public class SearchController {
    private final SearchDAO searchDAO;

    public SearchController(SearchDAO searchDAO) {
        this.searchDAO = searchDAO;
    }

    @GetMapping
    public String search(@RequestParam("query") String query,
                         @RequestParam(required = false) boolean check_author,
                         @RequestParam(required = false) boolean check_genre,
                         @RequestParam(required = false) String only,
                         Model model) {
        if (Integer.parseInt(only) == 0) {
            model.addAttribute("results", searchDAO.searchBooks(query, check_author, check_genre));
        }
        else if(check_author)
            model.addAttribute("aresults", searchDAO.searchAuthors(query));
        else if (check_genre)
            model.addAttribute("gresults", searchDAO.searchGenre(query));

        return "index";
    }
}
