package ie.planmyrun.api.planmyrunAPI.service;

import ie.planmyrun.api.planmyrunAPI.dto.AccountStatusResponse;
import ie.planmyrun.api.planmyrunAPI.dto.CreateAccountRequest;
import ie.planmyrun.api.planmyrunAPI.entity.Account;
import ie.planmyrun.api.planmyrunAPI.entity.AccountStatus;
import ie.planmyrun.api.planmyrunAPI.exception.AccountNotFoundException;
import ie.planmyrun.api.planmyrunAPI.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Checks whether a user's account is active.
 * Supports lookup by email, phone number, or numeric account ID.
 */
@Service
public class AccountStatusService {

    private static final Logger log = LoggerFactory.getLogger(AccountStatusService.class);

    private final AccountRepository accountRepository;

    @Autowired
    public AccountStatusService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Resolve account by user_identifier (email, phone, or account ID) and return status.
     * "Active" means status is ACTIVE (not suspended or deleted).
     */
    public AccountStatusResponse checkStatus(String userIdentifier) {
        String trimmed = userIdentifier != null ? userIdentifier.trim() : "";
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("user_identifier is required");
        }

        Account account = findAccount(trimmed);
        if (account == null) {
            log.info("Account status check: no account found for identifier (masked)");
            throw new AccountNotFoundException("No account found for the given identifier.");
        }

        if (account.isActive()) {
            log.info("Account status check: account active, id={}", account.getId());
            return AccountStatusResponse.active("Account in good standing.");
        }

        String statusLabel = account.getStatus().name().toLowerCase();
        log.info("Account status check: account not active, id={}, status={}", account.getId(), statusLabel);
        return AccountStatusResponse.inactive(
            statusLabel,
            "Account is " + statusLabel + ". Please contact support."
        );
    }

    /**
     * Look up account by email, phone, or numeric ID (in that order).
     * Supports: email, phone number, or account ID.
     */
    private Account findAccount(String userIdentifier) {
        // Try numeric account ID first
        if (userIdentifier.matches("\\d+")) {
            return accountRepository.findById(Long.parseLong(userIdentifier)).orElse(null);
        }
        // Try email (case-insensitive)
        return accountRepository.findByEmailIgnoreCase(userIdentifier)
            .or(() -> accountRepository.findByPhoneNumber(normalizePhone(userIdentifier)))
            .orElse(null);
    }

    private static String normalizePhone(String phone) {
        if (phone == null) return null;
        return phone.replaceAll("\\s+", "").replaceAll("[^0-9+]", "");
    }

    /**
     * Create a new account. At least one of email or phoneNumber must be provided.
     * Email and phoneNumber must be unique.
     */
    public Account createAccount(CreateAccountRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request body is required");
        }
        String email = request.getEmail();
        String phoneNumber = request.getPhoneNumber();
        if ((email == null || email.isBlank()) && (phoneNumber == null || phoneNumber.isBlank())) {
            throw new IllegalArgumentException("At least one of email or phoneNumber is required");
        }
        if (email != null && !email.isBlank() && accountRepository.findByEmailIgnoreCase(email).isPresent()) {
            throw new IllegalArgumentException("An account with this email already exists");
        }
        String normalizedPhone = normalizePhone(phoneNumber);
        if (normalizedPhone != null && !normalizedPhone.isBlank()
            && accountRepository.findByPhoneNumber(normalizedPhone).isPresent()) {
            throw new IllegalArgumentException("An account with this phone number already exists");
        }
        Account account = new Account();
        account.setEmail(email != null && !email.isBlank() ? email.trim() : null);
        account.setPhoneNumber(normalizedPhone);
        account.setStatus(request.getStatus() != null ? request.getStatus() : AccountStatus.ACTIVE);
        account = accountRepository.save(account);
        log.info("Account created, id={}", account.getId());
        return account;
    }
}
