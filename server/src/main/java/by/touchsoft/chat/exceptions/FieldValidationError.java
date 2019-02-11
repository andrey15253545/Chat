package by.touchsoft.chat.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldValidationError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    @Override
    public String toString() {
        return "{\nobject " + object +
                ",\nfield " + field +
                ",\nrejectedValue " + rejectedValue +
                ",\nmessage " + message +
                "\n}";
    }
}
