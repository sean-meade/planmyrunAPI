package ie.planmyrun.api.planmyrunAPI.exception;

/**
 * Thrown when no account is found for the given user_identifier.
 * Mapped to 404 with a body suitable for the voice agent.
 */
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
