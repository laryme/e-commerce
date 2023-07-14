package uz.spiders.ecommerce.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiResponse<E> {
    private boolean success;

    private String message;

    private E data;

    private List<ErrorData> errors;


    private ApiResponse(boolean success, String message, E data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    private ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    private ApiResponse(boolean success, E data) {
        this.success = success;
        this.data = data;
    }

    private ApiResponse(boolean success) {
        this.success = success;
    }

    public ApiResponse(List<ErrorData> errors) {
        this.errors = errors;
    }

    public static <T> ApiResponse<T> successResponse() {
        return new ApiResponse<>(true);
    }

    public static <T> ApiResponse<T> successResponse(String message) {
        return new ApiResponse<>(true, message);
    }

    public static <T> ApiResponse<T> successResponse(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> successResponse(T data) {
        return new ApiResponse<>(true, data);
    }


    public static ApiResponse<List<ErrorData>> failResponse(String msg, Integer code) {
        ErrorData errorData = new ErrorData(msg, code);
        return new ApiResponse<>(List.of(errorData));
    }

    public static ApiResponse<List<ErrorData>> failResponse(List<ErrorData> errors) {
        return new ApiResponse<>(errors);
    }

}