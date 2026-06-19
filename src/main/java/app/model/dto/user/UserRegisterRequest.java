package app.model.dto.user;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRegisterRequest {
    @NotBlank
    @Size(min = 4, max = 20, message = "Username must be at least 4 and max 20 characters.")
    private String username;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters.")
    private String password;

    @NotBlank
    @Email
    private String email;
}
