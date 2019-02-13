package by.touchsoft.chat.model;


import com.google.common.base.Objects;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Chat {

    private String chatId = UUID.randomUUID().toString().replace("-", "");
    private String userId;
    private String companionId;

    public Chat(String userId, String companionId) {
        this.userId = userId;
        this.companionId = companionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;
        Chat chat = (Chat) o;
        return Objects.equal(getUserId(), chat.getUserId()) &&
                Objects.equal(getCompanionId(), chat.getCompanionId()) ||
                Objects.equal(getUserId(), chat.getCompanionId()) &&
                Objects.equal(getCompanionId(), chat.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUserId(), getCompanionId());
    }
}
