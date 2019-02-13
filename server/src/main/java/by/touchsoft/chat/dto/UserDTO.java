package by.touchsoft.chat.dto;

import by.touchsoft.chat.model.Role;
import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    @NotNull
    private String name;
    @NotNull
    private Role Role;
}



