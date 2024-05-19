package my.books.DAO;

import my.books.models.Author;
import my.books.models.Book;
import my.books.models.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SearchDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SearchDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Iterable<Book> searchBooks(String query, boolean authorChecked, boolean genreChecked) {
        String SQL = """
    SELECT * FROM books b\s
    JOIN authors a ON b.author_id = a.id\s
    JOIN genres g ON b.genre_id = g.id\s
    WHERE LOWER (b_title) LIKE LOWER (?)""";

        if (authorChecked && genreChecked) {
            return jdbcTemplate.query(SQL +
                            "OR LOWER (name) LIKE (?) " +
                            "OR LOWER (g_title) LIKE (?)",
                    new BookMapper(), "%"+query+"%", "%"+query+"%", "%"+query+"%");

        } else if (authorChecked) {
            return jdbcTemplate.query(SQL + "OR LOWER (name) LIKE (?)",
                    new BookMapper(), "%"+query +"%", "%"+query+"%");

        } else if (genreChecked) {
            return jdbcTemplate.query(SQL +
                            "OR LOWER (g_title) LIKE (?)",
                    new BookMapper(),  "%"+query+"%", "%"+query+"%");

        } else {
            return jdbcTemplate.query(SQL,
                    new BookMapper(),"%" + query + "%");
        }
    }

    public Iterable<Author> searchAuthors(String query){
        return jdbcTemplate.query("SELECT * FROM authors WHERE LOWER (name) LIKE LOWER (?)",
                new BeanPropertyRowMapper<>(Author.class), "%"+query+"%");
    }

    public Iterable<Genre> searchGenre(String query){
        return jdbcTemplate.query("SELECT * FROM genres WHERE LOWER (g_title) LIKE LOWER (?)",
                new GenreMapper(), "%"+query+"%");
    }

}
