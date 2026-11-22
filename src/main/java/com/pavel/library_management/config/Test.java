package com.pavel.library_management.config;



import com.pavel.library_management.entity.Author;
import com.pavel.library_management.entity.Book;
import com.pavel.library_management.repository.AuthorRepository;
import com.pavel.library_management.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Test implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookService bookService;

    public Test(AuthorRepository authorRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) {
        if (!bookService.findAll().isEmpty()) {
            return;
        }

        Author author = authorRepository.save(new Author("Фёдор", "Достоевский"));

        Book book = new Book("Преступление и наказание", "978-5-17-090000-1",
                LocalDate.of(1866, 1, 1), 3);
        book.setAuthor(author);
        bookService.save(book);

        System.out.println("=== Книги в БД ===");
        bookService.findAll().forEach(b -> System.out.println(b.getTitle()));

        System.out.println("=== Поиск по названию 'преступ' ===");
        bookService.findByTitleContaining("преступ")
                .forEach(b -> System.out.println(b.getTitle()));
    }
}