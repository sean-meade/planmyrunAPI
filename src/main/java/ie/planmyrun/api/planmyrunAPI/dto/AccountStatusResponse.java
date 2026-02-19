package ie.planmyrun.api.planmyrunAPI.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Success response for account status check.
 * Caller can use "active" and/or "status"; optional "message" for the agent to relay.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountStatusResponse {

    private Boolean active;
    private String status;  // "active" | "inactive" | "suspended"
    private String message;

    public static AccountStatusResponse active(String message) {
        AccountStatusResponse r = new AccountStatusResponse();
        r.setActive(true);
        r.setStatus("active");
        r.setMessage(message);
        return r;
    }

    public static AccountStatusResponse inactive(String status, String message) {
        AccountStatusResponse r = new AccountStatusResponse();
        r.setActive(false);
        r.setStatus(status != null ? status : "inactive");
        r.setMessage(message);
        return r;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
