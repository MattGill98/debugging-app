package fish.payara.sample.debugging.service;

import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;

import fish.payara.sample.debugging.api.BookService;
import fish.payara.sample.debugging.model.Book;
import fish.payara.sample.debugging.model.ServiceModel;

/**
 * @author Matt Gill
 */
@RequestScoped
@Path("/books")
public class BookEndpoints implements BookService {

    @Inject
    private ServiceModel model;

    @Override
    public Collection<Book> getAllBooks() {
        return model.getBooks();
    }

    @Override
    public Book getBook(String id) {
        return model.getBooks().stream()
                .filter(book -> book.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new NotFoundException("Unable to find the book with the specified id."));
    }

    @Override
    public Book addBook(Book id) {
        return model.addBook(id);
    }

    @Override
    public Book updateBook(Book book) {
        return model.updateBook(book);
    }

    @Override
    public Book deleteBook(String id) {
        return model.deleteBook(id);
    }

}