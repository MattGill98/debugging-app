package fish.payara.sample.debugging.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import fish.payara.sample.debugging.api.BookService;
import fish.payara.sample.debugging.api.LibraryService;
import fish.payara.sample.debugging.model.Book;
import fish.payara.sample.debugging.model.Library;

public class IntegrationTest extends ClientTest {

    private BookService bookClient;
    private LibraryService libraryClient;

    @Before
    public void setupClient() {
        this.bookClient = restClient(BookService.class, BookEndpoints.class);
        this.libraryClient = restClient(LibraryService.class, LibraryEndpoints.class);
    }

    @Test
    public void when_book_added_to_library_expect_references_created() {
        final Library testLibrary = new Library("test library");
        final Book testBook = new Book("name", "author");

        libraryClient.addLibrary(testLibrary);
        libraryClient.addBookToLibrary(testLibrary.getId(), testBook);

        final Collection<Book> discoveredBooks = bookClient.getAllBooks();
        assertFalse("Book reference wasn't created: " + discoveredBooks, discoveredBooks.isEmpty());
    }

    @Test
    public void when_book_deleted_expect_no_references_remain() {
        final Library testLibrary = new Library("test library");
        final Book testBook = new Book("name", "author");

        libraryClient.addLibrary(testLibrary);
        libraryClient.addBookToLibrary(testLibrary.getId(), testBook);
        bookClient.deleteBook(testBook.getId());

        final Library discoveredLibrary = libraryClient.getLibrary(testLibrary.getId());
        assertTrue("Library contains invalid reference: " + discoveredLibrary, discoveredLibrary.getBooks().isEmpty());
    }
    
}
