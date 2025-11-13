package ecomhub.userservice.api.dto.request.user;

import java.time.LocalDate;

public record ChangeBirthDateRequest(LocalDate newBirthDate) {
}
