package uz.spiders.ecommerce.exception;

public class TokenExpiredOrInvalid extends RuntimeException {
    public TokenExpiredOrInvalid(String message) {
        super(message);
    }
}
