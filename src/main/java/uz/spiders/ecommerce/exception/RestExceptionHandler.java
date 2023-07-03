package uz.spiders.ecommerce.exception;


import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.spiders.ecommerce.payload.ApiResult;
import uz.spiders.ecommerce.payload.ErrorData;

import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = UsernameOrEmailAlreadyExists.class)
    public ResponseEntity<ApiResult<List<ErrorData>>> exceptionHandler(UsernameOrEmailAlreadyExists ex) {
        return new ResponseEntity<>(
                ApiResult
                        .failResponse(ex.getMessage(), HttpStatus.CONFLICT.value()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResult<List<ErrorData>>> exceptionHandler(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        return ResponseEntity.status(400)
                .body(
                        new ApiResult<>(
                                fieldErrors
                                        .stream()
                                        .map(r -> new ErrorData(r.getDefaultMessage(), r.getField()))
                                        .toList()));
       /* return new ResponseEntity<>(
                ApiResult
                        .failResponse(
                                ex.getBindingResult().getFieldErrors()
                                        .stream()
                                        .map(e -> new ErrorData(e.getDefaultMessage(), HttpStatus.BAD_REQUEST.value()))
                                        .collect(Collectors.toList())),
                HttpStatus.BAD_REQUEST);*/
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResult<?>> exceptionHandler(BadCredentialsException ex) {
        return new ResponseEntity<>(
                ApiResult
                        .failResponse(
                                "Username or password incorrect",
                                HttpStatus.UNAUTHORIZED.value()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ApiResult<?>> exceptionHandler(LockedException ex) {
        return new ResponseEntity<>(
                ApiResult
                        .failResponse(
                                "Account is blocked due to some causes",
                                HttpStatus.UNAUTHORIZED.value()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiResult<?>> exceptionHandler(DisabledException ex) {
        return new ResponseEntity<>(
                ApiResult
                        .failResponse(
                                "Account has not been activated yet. Please activate your account",
                                HttpStatus.UNAUTHORIZED.value()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenExpiredOrInvalid.class)
    public ResponseEntity<ApiResult<?>> exceptionHandler(TokenExpiredOrInvalid ex) {
        return new ResponseEntity<>(
                ApiResult
                        .failResponse(
                                "The link you have clicked on is no longer valid. Please request a new link from the sender.",
                                HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<ApiResult<?>> exceptionHandler(EmailNotVerifiedException ex) {
        return new ResponseEntity<>(
                ApiResult
                        .failResponse(
                                "Your email address has not been verified. In order to access our system, you need to verify your email address first. We have sent a verification link to the email address you provided during registration. Please check your email and follow the instructions in the email to verify your account. If you do not see the email in your inbox, please check your spam or junk folder.",
                                HttpStatus.CONFLICT.value()),
                HttpStatus.CONFLICT);

    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<ApiResult<?>> exceptionHandler(EmailSendingException ex) {
        return new ResponseEntity<>(
                ApiResult
                        .failResponse(
                                ex.getMessage(),
                                HttpStatus.CONFLICT.value()),
                HttpStatus.CONFLICT);

    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiResult<?>> exceptionHandler(DataNotFoundException ex) {
        return new ResponseEntity<>(
                ApiResult
                        .failResponse(
                                ex.getMessage(),
                                HttpStatus.NOT_FOUND.value()),
                HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ApiResult<?>> exceptionHandler(InvalidParameterException ex) {
        return new ResponseEntity<>(
                ApiResult
                        .failResponse(
                                ex.getMessage(),
                                HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResult<?>> exceptionHandler(ExpiredJwtException ex) {
        return new ResponseEntity<>(
                ApiResult
                        .failResponse(
                                ex.getMessage(),
                                HttpStatus.BAD_REQUEST.value()),
                HttpStatus.NOT_FOUND);

    }
}
