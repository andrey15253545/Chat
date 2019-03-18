package by.touchsoft.chat.exceptions;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class ApiError {

    private HttpStatus status;
    private String message;
    private List<FieldValidationError> fieldValidationErrors;


    ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }


    void addValidationErrors(List<FieldError> fieldErrors) {
        for (FieldError error : fieldErrors) {
            FieldValidationError subError = new FieldValidationError();
            subError.setField(error.getField());
            subError.setMessage(error.getDefaultMessage());
            subError.setRejectedValue(error.getRejectedValue());
            subError.setObject(error.getObjectName());
            this.addSubError(subError);
        }
    }

    private void addSubError(FieldValidationError subError) {
        if (fieldValidationErrors == null) {
            fieldValidationErrors = new ArrayList<>();
        }
        fieldValidationErrors.add(subError);
    }

    @Override
    public String toString() {
        return "status " + status +
                ",\ntext " + message +
                " :\n "+ fieldValidationErrors.toString();
    }
}