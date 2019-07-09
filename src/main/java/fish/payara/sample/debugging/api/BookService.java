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

/**
 * @author Matt Gill
 */
public interface BookService {

    @GET
    public Collection<Book> getAllBooks();

    @GET
    @Path("/{id}")
    public Book getBook(@PathParam("id") String id);

    @POST
    public Book addBook(@Valid Book book);

    @DELETE
    @Path("/{id}")
    public Book deleteBook(@PathParam("id") String id);

    @PUT
    public Book updateBook(@Valid Book book);

}