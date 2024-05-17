package my.books.controllers;

import my.books.DAO.AuthorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorDAO authorDAO;

    @Autowired
    public AuthorController(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @GetMapping()
    public String allAuthors(Model model) {
        model.addAttribute("authors", authorDAO.findAll());
        return "authors/allAuthors";
    }

    @GetMapping("{id}")
    public String anAuthor(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorDAO.findById(id));
        model.addAttribute("authorsBooks", authorDAO.findAuthorsBooks(id));
        return "authors/anAuthor";
    }

}
