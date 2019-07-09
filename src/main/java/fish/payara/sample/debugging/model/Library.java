package fish.payara.sample.debugging.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.json.Json;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Matt Gill
 */
public class Library {

    private String id;

    @NotBlank
    private String name;

    @NotNull
    private Collection<Book> books;

    public Library(String name) {
        this.id = ServiceModel.getRandomId();
        this.name = name;
        this.books = new ArrayList<>();
    }

    public Library() {
        this(null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Book> getBooks() {
        return books;
    }

    public void setBooks(Collection<Book> books) {
        this.books = books;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((books == null) ? 0 : books.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Library other = (Library) obj;
        if (books == null) {
            if (other.books != null)
                return false;
        } else if (!books.equals(other.books))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return Json.createObjectBuilder()
            .add("id", id)
            .add("name", name)
            .add("books", Arrays.toString(books.toArray()))
            .build()
            .toString();
    }
}