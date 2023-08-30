package net.therap.estaurant.exception;

/**
 * @author nadimmahmud
 * @since 2/2/23
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Resource not found");
    }
}
