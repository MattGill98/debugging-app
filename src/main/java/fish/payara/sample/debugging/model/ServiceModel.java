package fish.payara.sample.debugging.model;

import java.util.Collection;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

/**
 * @author Matt Gill
 */
@ApplicationScoped
public class ServiceModel {

    private Collection<Book> books;
    private Collection<Library> libraries;

    // Put business logic here

    protected static String getRandomId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

}