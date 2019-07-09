package fish.payara.sample.debugging.service;

import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
        return null;
    }

    @Override
    public Book getBook(String id) {
        return null;
    }

    @Override
    public Book addBook(Book id) {
        return null;
    }

    @Override
    public Book updateBook(Book book) {
        return null;
    }

    @Override
    public Book deleteBook(String id) {
        return null;
    }

}