package fish.payara.sample.debugging.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import fish.payara.sample.debugging.api.BookService;
import fish.payara.sample.debugging.model.Book;

public class BookEndpointTest extends ClientTest {

    private BookService client;

    @Before
    public void setupClient() {
        this.client = restClient(BookService.class, BookEndpoints.class);
    }

    @Test
    public void when_get_books_expect_empty() {
        final Collection<Book> results = client.getAllBooks();
        assertNotNull("Null result", results);
        assertTrue("Results were not empty", results.isEmpty());
    }

    @Test
    public void when_add_book_expect_persisted() {
        final Book testBook = new Book("test book", "test author");

        client.addBook(testBook);
        Collection<Book> results = client.getAllBooks();
        assertTrue("Posted book wasn't persisted. Contents: " + Arrays.toString(results.toArray()), results.contains(testBook));
    }
    

    @Test
    public void when_add_book_twice_expect_only_one_persisted() {
        final Book testBook = new Book("test book", "test author");

        client.addBook(testBook);
        client.addBook(testBook);
        Collection<Book> results = client.getAllBooks();
        assertEquals("Book was persisted an incorrect number of times", 1, results.size());
    }

    @Test
    public void when_update_book_expect_persisted() {
        final Book testBook = new Book("test book", "test author");

        client.addBook(testBook);
        testBook.setName("new test book");
        final Book updatedBook = client.updateBook(testBook);
        assertEquals("The wrong book was updated", testBook, updatedBook);

        Collection<Book> results = client.getAllBooks();
        assertTrue("Updated book wasn't persisted. Contents: " + Arrays.toString(results.toArray()), results.contains(testBook));
    }

    @Test
    public void when_remove_book_expect_persisted() {
        final Book testBook = new Book("test book", "test author");

        client.addBook(testBook);
        final Book deletedBook = client.deleteBook(testBook.getId());
        assertEquals("The wrong book was deleted", testBook, deletedBook);

        Collection<Book> results = client.getAllBooks();
        assertFalse("Deleted book wasn't persisted. Contents: " + Arrays.toString(results.toArray()), results.contains(testBook));
    }

    @Test
    public void when_book_to_get_doesnt_exist_expect_404() {
        assertFailStatus(404, () -> client.getBook("blahblahblah"));
    }

    @Test
    public void when_book_to_remove_doesnt_exist_expect_404() {
        assertFailStatus(404, () -> client.deleteBook("blahblahblah"));
    }

    @Test
    public void when_book_is_invalid_expect_422() {
        assertFailStatus(422, () -> client.addBook(new Book(null, null)));
    }

}
