package app.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static app.messages.ErrorMessages.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    @NotBlank(message = USERNAME_REQUIRED)
    @Size(min = 4, max = 20, message = USERNAME_SIZE)
    private String username;

    @NotBlank(message = PASSWORD_REQUIRED)
    @Size(min = 6, message = PASSWORD_SIZE)
    private String password;

    @NotBlank(message = EMAIL_REQUIRED)
    @Email
    private String email;

    private String profilePicture;

    private String bio;
}
