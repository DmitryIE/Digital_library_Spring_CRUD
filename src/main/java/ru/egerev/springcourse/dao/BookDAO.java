package ru.egerev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.egerev.springcourse.models.Book;
import ru.egerev.springcourse.models.LibraryReader;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;
    private final LibraryReaderDAO libraryReaderDAO;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate, LibraryReaderDAO libraryReaderDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.libraryReaderDAO = libraryReaderDAO;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(title, author, year) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updateBook) {
        jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=? WHERE id=?",
                updateBook.getTitle(), updateBook.getAuthor(), updateBook.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    public void freeBook(int id) {
        jdbcTemplate.update("UPDATE Book SET reader=? WHERE id=?", null, id);
    }

    public void takeBook(int idBook, String readerName) {
        jdbcTemplate.update("UPDATE Book SET reader=? WHERE id=?", readerName, idBook);
    }

    public List<LibraryReader> allReaders () {
        return libraryReaderDAO.index();
    }

}
