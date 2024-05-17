package my.books.DAO;

import my.books.models.Author;
import my.books.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Author> findAll() {
        return jdbcTemplate.query("SELECT * FROM authors",
                new BeanPropertyRowMapper<>(Author.class));
    }

    public Author findById(int id) {
        return jdbcTemplate.query("SELECT * FROM authors WHERE id = ?",
                        new BeanPropertyRowMapper<>(Author.class), new Object[]{id})
                .stream().findAny().orElse(null);
    }

    public Iterable<Book> findAuthorsBooks(int authorId) {
        return jdbcTemplate.query(
                "SELECT b.id, b.b_title, g.g_title, a.name FROM books b " +
                        "JOIN genres g ON b.genre_id = g.id " +
                        "JOIN authors a ON b.author_id = a.id " + // поленился менять BookMapper
                        "WHERE b.author_id =?",
                new BookMapper(), authorId);
    }
}
