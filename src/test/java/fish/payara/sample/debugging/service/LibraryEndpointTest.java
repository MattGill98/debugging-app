package fish.payara.sample.debugging.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import fish.payara.sample.debugging.api.LibraryService;
import fish.payara.sample.debugging.model.Book;
import fish.payara.sample.debugging.model.Library;

public class LibraryEndpointTest extends ClientTest {

    private LibraryService client;

    @Before
    public void setupClient() {
        this.client = restClient(LibraryService.class, LibraryEndpoints.class);
    }

    @Test
    public void when_get_libraries_expect_empty() {
        final Collection<Library> results = client.getAllLibraries();
        assertNotNull("Null result", results);
        assertTrue("Results were not empty", results.isEmpty());
    }

    @Test
    public void when_add_library_expect_persisted() {
        final Library testLibrary = new Library("test library");

        client.addLibrary(testLibrary);
        Collection<Library> results = client.getAllLibraries();
        assertTrue("Posted library wasn't persisted. Contents: " + Arrays.toString(results.toArray()), results.contains(testLibrary));
    }

    @Test
    public void when_update_library_expect_persisted() {
        final Library testLibrary = new Library("test library");

        client.addLibrary(testLibrary);
        testLibrary.setName("new test library");
        final Library updatedLibrary = client.updateLibrary(testLibrary);
        assertEquals("The wrong library was updated", testLibrary, updatedLibrary);

        Collection<Library> results = client.getAllLibraries();
        assertTrue("Updated library wasn't persisted. Contents: " + Arrays.toString(results.toArray()), results.contains(testLibrary));
    }

    @Test
    public void when_remove_library_expect_persisted() {
        final Library testLibrary = new Library("test library");

        client.addLibrary(testLibrary);
        final Library deletedLibrary = client.deleteLibrary(testLibrary.getId());
        assertEquals("The wrong library was deleted", testLibrary, deletedLibrary);

        Collection<Library> results = client.getAllLibraries();
        assertFalse("Deleted library wasn't persisted. Contents: " + Arrays.toString(results.toArray()), results.contains(testLibrary));
    }

    @Test
    public void when_add_book_to_library_expect_persisted() {
        final Library testLibrary = new Library("test library");
        final Book testBook = new Book("name", "author");

        client.addLibrary(testLibrary);
        testLibrary.getBooks().add(testBook);
        final Library updatedLibrary = client.addBookToLibrary(testLibrary.getId(), testBook);
        assertArrayEquals("Book wasn't persisted in library", testLibrary.getBooks().toArray(), updatedLibrary.getBooks().toArray());
    }

    @Test
    public void when_add_book_to_library_twice_expect_only_one_persisted() {
        final Library testLibrary = new Library("test library");
        final Book testBook = new Book("name", "author");

        client.addLibrary(testLibrary);
        testLibrary.getBooks().add(testBook);
        Library updatedLibrary = client.addBookToLibrary(testLibrary.getId(), testBook);
        updatedLibrary = client.addBookToLibrary(testLibrary.getId(), testBook);
        assertArrayEquals("Book was persisted an incorrect number of times", testLibrary.getBooks().toArray(),
                updatedLibrary.getBooks().toArray());
    }

    @Test
    public void when_library_to_get_doesnt_exist_expect_404() {
        assertFailStatus(404, () -> client.getLibrary("blahblahblah"));
    }

    @Test
    public void when_library_to_remove_doesnt_exist_expect_404() {
        assertFailStatus(404, () -> client.deleteLibrary("blahblahblah"));
    }

    @Test
    public void when_library_to_add_to_doesnt_exist_expect_404() {
        assertFailStatus(404, () -> client.addBookToLibrary("blahblahblah", new Book("name", "author")));
    }

    @Test
    public void when_library_is_invalid_expect_422() {
        assertFailStatus(422, () -> client.addLibrary(new Library(null)));
    }

}
