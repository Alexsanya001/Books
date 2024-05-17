package my.books.controllers;

import my.books.DAO.BookDAO;
import my.books.DAO.GenreDAO;
import my.books.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final GenreDAO genreDAO;

    @Autowired
    public BookController(BookDAO bookDAO, GenreDAO genreDAO){
        this.bookDAO = bookDAO;
        this.genreDAO = genreDAO;
    }

    @GetMapping("/allBooks")
    public String allBooks(@ModelAttribute("book") Book book, Model model){
        model.addAttribute("allBooks", bookDAO.findAll());
        model.addAttribute("genres", genreDAO.findAll());
        return "books/allBooks";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDAO.findById(id));
        return "books/showBook";
    }

    @PostMapping
    public String save(@ModelAttribute("a_book") Book book, Model model){
        model.addAttribute("id", bookDAO.save(book));
        return "redirect:/books/allBooks";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books/allBooks";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDAO.findById(id));
        return "books/edit";
    }

    @PatchMapping("{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") Book book){
        bookDAO.update(book, id);
        return "redirect:/books/allBooks";
    }
}
