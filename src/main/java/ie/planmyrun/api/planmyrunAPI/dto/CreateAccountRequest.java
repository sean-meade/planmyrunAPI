package ie.planmyrun.api.planmyrunAPI.dto;

import ie.planmyrun.api.planmyrunAPI.entity.AccountStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * Request body for creating an account.
 * At least one of email or phoneNumber must be provided.
 */
public class CreateAccountRequest {

    @Email(message = "email must be a valid email address")
    @Size(max = 255)
    private String email;

    @Size(max = 50)
    private String phoneNumber;

    private AccountStatus status = AccountStatus.ACTIVE;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null ? email.trim().isEmpty() ? null : email.trim() : null;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber != null ? phoneNumber.trim().isEmpty() ? null : phoneNumber.trim() : null;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status != null ? status : AccountStatus.ACTIVE;
    }
}
