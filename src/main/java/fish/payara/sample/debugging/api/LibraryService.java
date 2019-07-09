package fish.payara.sample.debugging.api;

import java.util.Collection;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import fish.payara.sample.debugging.model.Book;
import fish.payara.sample.debugging.model.Library;

/**
 * @author Matt Gill
 */
public interface LibraryService {

    @GET
    public Collection<Library> getAllLibraries();

    @GET
    @Path("/{name}")
    public Library getLibrary(@PathParam("name") String name);

    @POST
    public Library addLibrary(@Valid Library library);

    @POST
    @Path("/{name}")
    public Book addBookToLibrary(@PathParam("name") String name, Book book);

    @DELETE
    @Path("/{name}")
    public Library deleteLibrary(@PathParam("name") String name);

}