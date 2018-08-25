package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Validated
public class BookServiceImpl implements BookService{
    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository repository;

    @Autowired
    public BookServiceImpl(final BookRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Book saveBook(@NotNull @Valid Book book){
        LOGGER.debug("Creating {}", book);
        if (repository.findById(book.getId()).isPresent()) {
            LOGGER.error("There already exists a book with id={}", book.getId());
            return book;
        }
        return repository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getList() {
        LOGGER.debug("Retrieving the list of all users");
        return repository.findAll();
    }

    @Override
    public Book getBook(Long bookId) {
        return repository.findById(bookId).get();
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        LOGGER.debug("deleting {}", bookId);
        repository.deleteById(bookId);
    }
}
