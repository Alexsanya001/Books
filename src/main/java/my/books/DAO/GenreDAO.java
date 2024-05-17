package my.books.DAO;

import my.books.models.Book;
import my.books.models.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreDAO {

    private final JdbcTemplate jdbcTemplate;

    public GenreDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Genre> findAll() {
        return jdbcTemplate.query("SELECT * FROM genres", new GenreMapper());
    }

    public Genre findByTitle(String title) {
        return jdbcTemplate.query("SELECT * FROM genres WHERE g_title=?",
                new GenreMapper(), title).stream().findAny().orElse(null);
    }

    public Iterable<Book> genreBooks(String g_title) {
        return jdbcTemplate.query("SELECT b.id, b.b_title, a.name, g.g_title " +
                        "FROM books b " +
                        "JOIN authors a ON b.author_id = a.id " +
                        "JOIN genres g ON b.genre_id = g.id " +
                        "WHERE b.genre_id = (SELECT id FROM genres WHERE g_title = ?)",
                new BookMapper(), g_title);
    }

}
