package fish.payara.sample.debugging.service;

import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
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
        return null;
    }

    @Override
    public Library getLibrary(String id) {
        return null;
    }

    @Override
    public Library addLibrary(Library library) {
        return null;
    }

    @Override
    public Library deleteLibrary(String id) {
        return null;
    }

    @Override
    public Library updateLibrary(@Valid Library library) {
        return null;
    }

    @Override
    public Library addBookToLibrary(String id, Book book) {
        return null;
    }

}