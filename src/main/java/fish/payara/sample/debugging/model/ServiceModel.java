package fish.payara.sample.debugging.model;

import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.LinkedHashSet;

import javax.enterprise.context.ApplicationScoped;

/**
 * A class to act as a temporary database for the application and provide
 * convenience methods for accessing the data.
 * 
 * @author Matt Gill
 */
@ApplicationScoped
public class ServiceModel {

    private Collection<Book> books;
    private Collection<Library> libraries;

    public ServiceModel() {
        this.books = new LinkedHashSet<>();
        this.libraries = new LinkedHashSet<>();
    }

    /**
     * @return all books across all libraries.
     */
    public Collection<Book> getBooks() {
        return unmodifiableCollection(books);
    }

    /**
     * @return all libraries.
     */
    public Collection<Library> getLibraries() {
        return libraries;
    }

    /**
     * Adds a book to the store.
     * 
     * @param book the book to add.
     * @return the added book.
     */
    public Book addBook(Book book) {
        books.add(book);
        return book;
    }

}