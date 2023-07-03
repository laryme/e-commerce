package uz.spiders.ecommerce.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorData {
    private String msg;
    private Integer code;
    private String fieldName;

    public ErrorData(String msg, String fieldName) {
        this.msg = msg;
        this.fieldName = fieldName;
    }

    public ErrorData(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }
}
