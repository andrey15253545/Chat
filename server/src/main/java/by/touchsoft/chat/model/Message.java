package by.touchsoft.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * The object of this class stores the sender, text and date of the text.
 */

@Data
@AllArgsConstructor
public class Message implements Serializable {

    private static final String MESSAGE_FORMAT = "%s : %s";
    private String name;
    private Date date;
    private String text;

    @Override
    public String toString() {
        return name != null ? String.format(MESSAGE_FORMAT, name, text) : text;
    }
}
