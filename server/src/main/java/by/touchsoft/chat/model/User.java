package by.touchsoft.chat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class User {

    private String id = UUID.randomUUID().toString().replace("-", "");
    private Role role = Role.GUEST;
    private String name = "user";

    public User (String name, Role role){
        this.name = name;
        this.role = role;
    }

    @Override
    public String toString() {
        return "user{" +
                "id='" + id + '\'' +
                ", role=" + role +
                ", name='" + name + '\'' +
                '}';
    }

}
