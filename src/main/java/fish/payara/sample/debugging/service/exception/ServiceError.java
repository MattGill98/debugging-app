package fish.payara.sample.debugging.service.exception;

/**
 * @author Matt Gill
 */
public class ServiceError {

    private String type;
    private String message;
    private String[] details;

    protected <T extends Throwable> ServiceError(Class<T> errorType, String message, String... details) {
        this.type = errorType.getSimpleName();
        this.message = message;
        this.details = details;
    }

    protected ServiceError(Throwable throwable) {
        this(throwable.getClass(), throwable.getMessage());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getDetails() {
        return details;
    }

    public void setDetails(String[] details) {
        this.details = details;
    }

}