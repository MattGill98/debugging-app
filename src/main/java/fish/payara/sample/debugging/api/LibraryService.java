package fish.payara.sample.debugging.api;

import java.util.Collection;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
    @Path("/{id}")
    public Library getLibrary(@PathParam("id") String id);

    @POST
    public Library addLibrary(@Valid Library library);

    @DELETE
    @Path("/{id}")
    public Library deleteLibrary(@PathParam("id") String id);

    @PUT
    public Library updateLibrary(@Valid Library library);

    @POST
    @Path("/{id}")
    public Library addBookToLibrary(@PathParam("id") String id, Book book);

}