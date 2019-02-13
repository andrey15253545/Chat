package by.touchsoft.chat.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

/**
 * The object of this class stores the sender, text and date of the message.
 */

public class Message {

    private static final String MESSAGE_FORMAT = "%s : %s";
    @JsonSerialize
    private User user;
    private Date date;
    private String message;

    public Message(User user, Date date, String mess) {
        this.user = user;
        this.date = date;
        this.message = mess;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return message;
    }

    @Override
    public String toString() {
        return String.format(MESSAGE_FORMAT, user.getName(), message);
    }
}
