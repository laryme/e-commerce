package uz.spiders.ecommerce.exception;

public class InvalidJwtException extends RuntimeException {
    public InvalidJwtException(String msg) {
        super(msg);
    }
}
