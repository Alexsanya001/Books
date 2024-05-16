package my.books.DAO;

import my.books.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    BookDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> findAll(){
      return jdbcTemplate.query("SELECT b.id, b.b_title, a.name, g.g_title FROM books b " +
                "JOIN authors a ON b.author_id = a.id " +
                "JOIN genres g ON b.genre_id = g.id", new BookMapper());
    }

    public Book show (int id){
        return jdbcTemplate.query("SELECT b.id, b.b_title, a.name, g.g_title " +
                "from books b " +
                "join authors a on b.author_id = a.id " +
                "join genres g on b.genre_id = g.id " +
                "WHERE b.id = ?", new Object[]{id}, new BookMapper())
                .stream().findAny().orElse(null);
    }

    public int save (Book book){
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        String sql = """
                WITH new_author AS (
                    INSERT INTO authors (name)
                           VALUES (?)
                           ON CONFLICT DO NOTHING
                           RETURNING id
                ),
                    new_genre AS (
                        INSERT INTO  genres(g_title)
                               VALUES (?)
                               ON CONFLICT DO NOTHING
                               RETURNING id
                    )
                INSERT INTO books (b_title, author_id, genre_id)
                VALUES (?,
                        COALESCE((SELECT id FROM new_author), (SELECT id FROM authors WHERE name = ?)),
                        COALESCE((SELECT id FROM new_genre), (SELECT id FROM genres WHERE g_title = ?)));""";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getAuthor().getName());
            ps.setString(2, book.getGenre().name());
            ps.setString(3, book.getTitle());
            ps.setString(4,book.getAuthor().getName());
            ps.setString(5, book.getGenre().name());
                return ps;

        }, generatedKeyHolder);
        return (int) Objects.requireNonNull(generatedKeyHolder.getKeys()).get("id");
    }

}
