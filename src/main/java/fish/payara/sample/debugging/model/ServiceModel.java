package fish.payara.sample.debugging.model;

import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.UUID;
import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;

/**
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

    public Collection<Book> getBooks() {
        return unmodifiableCollection(books);
    }

    public Collection<Library> getLibraries() {
        return unmodifiableCollection(libraries);
    }

    public Book addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
            return book;
        }
        return null;
    }

    public Book deleteBook(String id) {

        // Remove the actual book
        final Book book = removeBookFrom(books, id);
        if (book == null) {
            throw new IllegalArgumentException("Book to delete not found");
        }

        // Remove references to the book
        final Iterator<Library> libraryIterator = libraries.iterator();
        while (libraryIterator.hasNext()) {
            final Library library = libraryIterator.next();
            removeBookFrom(library.getBooks(), id);
        }

        return book;
    }

    public Book updateBook(Book book) {
        final String id = book.getId();

        // Find the book
        final Book discoveredBook = getBookFrom(books, id);
        if (discoveredBook == null) {
            throw new IllegalArgumentException("Book to update not found");
        }

        // Update the book
        discoveredBook.setName(book.getName());
        discoveredBook.setAuthor(book.getAuthor());

        return discoveredBook;
    }

    public Library addLibrary(Library library) {
        this.libraries.add(library);
        return library;
    }

    public Library deleteLibrary(String id) {
        final Library library = getLibrary(id, iterator -> iterator.remove());
        if (library == null) {
            throw new IllegalArgumentException("Library to delete not found");
        }
        return library;
    }

    public Library updateLibrary(Library library) {
        final String id = library.getId();

        // Find the library
        final Library discoveredLibrary = getLibrary(id);
        if (discoveredLibrary == null) {
            throw new IllegalArgumentException("Library to delete not found");
        }

        // Update the library
        discoveredLibrary.setName(library.getName());

        return discoveredLibrary;
    }
    
    private Library getLibrary(String id) {
        return getLibrary(id, null);
    }

    private Library getLibrary(String id, Consumer<Iterator<Library>> callback) {
        Iterator<Library> iterator = libraries.iterator();
        while (iterator.hasNext()) {
            final Library library = iterator.next();
            if (library.getId().equals(id)) {
                if (callback != null){
                    callback.accept(iterator);
                }
                return library;
            }
        }
        return null;
    }

    private static Book removeBookFrom(Iterable<Book> collection, String id) {
        return getBookFrom(collection, id, iterator -> iterator.remove());
    }

    private static Book getBookFrom(Iterable<Book> collection, String id) {
        return getBookFrom(collection, id, null);
    }

    private static Book getBookFrom(Iterable<Book> collection, String id, Consumer<Iterator<Book>> callback) {
        final Iterator<Book> bookIterator = collection.iterator();
        while (bookIterator.hasNext()) {
            final Book book = bookIterator.next();
            if (book.getId().equals(id)) {
                if (callback != null) {
                    callback.accept(bookIterator);
                }
                return book;
            }
        }
        return null;
    }

    protected static String getRandomId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

}