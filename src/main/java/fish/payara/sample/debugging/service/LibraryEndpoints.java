package fish.payara.sample.debugging.service;

import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;

import fish.payara.sample.debugging.api.LibraryService;
import fish.payara.sample.debugging.model.Book;
import fish.payara.sample.debugging.model.Library;
import fish.payara.sample.debugging.model.ServiceModel;

/**
 * @author Matt Gill
 */
@RequestScoped
@Path("/libraries")
public class LibraryEndpoints implements LibraryService {

    @Inject
    private ServiceModel model;

    @Override
    public Collection<Library> getAllLibraries() {
        return model.getLibraries();
    }

    @Override
    public Library getLibrary(String id) {
        return model.getLibraries().stream()
                .filter(library -> library.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new NotFoundException("Unable to find the library with the specified id."));
    }

    @Override
    public Library addLibrary(Library library) {
        model.addLibrary(library);
        return library;
    }

    @Override
    public Library deleteLibrary(String id) {
        return model.deleteLibrary(id);
    }

    @Override
    public Library updateLibrary(@Valid Library library) {
        return model.updateLibrary(library);
    }

    @Override
    public Library addBookToLibrary(String id, Book book) {
        final Library library = getLibrary(id);
        final Collection<Book> libraryBooks = library.getBooks();
        if (!libraryBooks.contains(book)) {
            libraryBooks.add(book);
            model.addBook(book);
        }
        return library;
    }

}