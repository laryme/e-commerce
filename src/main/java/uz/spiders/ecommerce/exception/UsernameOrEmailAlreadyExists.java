package uz.spiders.ecommerce.exception;

public class UsernameOrEmailAlreadyExists extends RuntimeException {
    public UsernameOrEmailAlreadyExists(String message) {
        super(message);
    }
}