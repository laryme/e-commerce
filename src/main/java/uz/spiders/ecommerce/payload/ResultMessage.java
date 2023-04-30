package uz.spiders.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMessage {
    private boolean status;
    private List<String> message;

    public ResultMessage(boolean status, String... message) {
        this.status = status;
        this.message = List.of(message);
    }
}
