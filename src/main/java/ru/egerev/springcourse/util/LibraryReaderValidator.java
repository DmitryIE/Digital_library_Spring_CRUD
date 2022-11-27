package ru.egerev.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.egerev.springcourse.dao.LibraryReaderDAO;
import ru.egerev.springcourse.models.LibraryReader;

@Component
public class LibraryReaderValidator implements Validator {

    private final LibraryReaderDAO libraryReaderDAO;

    @Autowired
    public LibraryReaderValidator(LibraryReaderDAO libraryReaderDAO) {
        this.libraryReaderDAO = libraryReaderDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return LibraryReader.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        LibraryReader libraryReader = (LibraryReader) o;
        if (libraryReaderDAO.show(libraryReader.getName()).isPresent()) {
            errors.rejectValue("name", "", "Эта ФИО уже используется");
        }
    }
}
