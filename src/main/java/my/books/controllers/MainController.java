package my.books.controllers;

import my.books.DAO.BookDAO;
import my.books.models.Author;
import my.books.models.Book;
import my.books.models.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {

    private final BookDAO bookDAO;

    @Autowired
    public MainController(BookDAO bookDAO){
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String index(@ModelAttribute("a_book") Book book,
                        @ModelAttribute("an_author") Author author, Model model) {
        model.addAttribute("genres", Genre.values());
        return "index";
    }




}
