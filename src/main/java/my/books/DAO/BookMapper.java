package my.books.DAO;

import my.books.models.Author;
import my.books.models.Book;
import my.books.models.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        if (rs.getMetaData().getColumnName(1).equals("id")) {
            book.setId(rs.getInt("id"));
        }
        book.setTitle(rs.getString("b_title"));
        book.setAuthor(new Author(rs.getString("name")));
        book.setGenre(Genre.valueOf(rs.getString("g_title")));

        return book;
    }
}
