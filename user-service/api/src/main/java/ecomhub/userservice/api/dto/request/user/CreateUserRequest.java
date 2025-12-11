package ecomhub.userservice.api.dto.request.user;

import lombok.Data;
import java.util.List;

@Data
public class CreateUserRequest {
    private String username;
    private String email;
    private String password;
    private List<String> roles;
}
