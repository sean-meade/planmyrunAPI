package ie.planmyrun.api.planmyrunAPI.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request body for account status check (e.g. Retell voice AI).
 * user_identifier can be: email, phone number, or numeric account ID.
 */
public class AccountStatusRequest {

    @NotBlank(message = "user_identifier is required")
    @Size(max = 255)
    private String user_identifier;

    public String getUser_identifier() {
        return user_identifier;
    }

    public void setUser_identifier(String user_identifier) {
        this.user_identifier = user_identifier;
    }
}
