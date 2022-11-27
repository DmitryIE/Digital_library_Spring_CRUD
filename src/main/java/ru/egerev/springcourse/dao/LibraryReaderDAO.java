package ru.egerev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.egerev.springcourse.models.Book;
import ru.egerev.springcourse.models.LibraryReader;

import java.util.List;
import java.util.Optional;

@Component
public class LibraryReaderDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LibraryReaderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<LibraryReader> index() {
        return jdbcTemplate.query("SELECT * FROM LibraryReader", new BeanPropertyRowMapper<>(LibraryReader.class));
    }

    public LibraryReader show(int id) {
        return jdbcTemplate.query("SELECT * FROM LibraryReader WHERE id=?", new Object[]{id},
                        new BeanPropertyRowMapper<>(LibraryReader.class))
                .stream().findAny().orElse(null);
    }

    public Optional<LibraryReader> show(String name) {
        return jdbcTemplate.query("SELECT * FROM LibraryReader WHERE name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(LibraryReader.class)).stream().findAny();
    }

    public void save(LibraryReader libraryReader) {
        jdbcTemplate.update("INSERT INTO LibraryReader(name, birthYear) VALUES (?, ?)",
                libraryReader.getName(), libraryReader.getBirthYear());
    }

    public void update(int id, LibraryReader updateLibraryReader) {
        jdbcTemplate.update("UPDATE LibraryReader SET name=?, birthYear=? WHERE id=?",
                updateLibraryReader.getName(), updateLibraryReader.getBirthYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM LibraryReader WHERE id=?", id);
    }

    public List<Book> takenBooks(int id) {
        String nameReader = show(id).getName();
        return jdbcTemplate.query("SELECT book.title, book.author, book.year FROM book " +
                "LEFT JOIN libraryreader on libraryreader.name = book.reader WHERE " +
                "name = ?", new BeanPropertyRowMapper<>(Book.class), nameReader);
    }

}
