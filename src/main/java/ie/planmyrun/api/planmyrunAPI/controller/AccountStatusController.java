package ie.planmyrun.api.planmyrunAPI.controller;

import ie.planmyrun.api.planmyrunAPI.dto.AccountStatusRequest;
import ie.planmyrun.api.planmyrunAPI.dto.AccountStatusResponse;
import ie.planmyrun.api.planmyrunAPI.dto.CreateAccountRequest;
import ie.planmyrun.api.planmyrunAPI.entity.Account;
import ie.planmyrun.api.planmyrunAPI.service.AccountStatusService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST endpoint for account status check (e.g. Retell voice AI).
 * POST /api/account/status with JSON body { "user_identifier": "email|phone|id" }.
 * Protected by API key (X-API-Key header); only server-to-server callers should use it.
 */
@RestController
@RequestMapping("/api/account")
public class AccountStatusController {

    private static final Logger log = LoggerFactory.getLogger(AccountStatusController.class);

    private final AccountStatusService accountStatusService;

    @Autowired
    public AccountStatusController(AccountStatusService accountStatusService) {
        this.accountStatusService = accountStatusService;
    }

    @PostMapping(
        value = "/status",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AccountStatusResponse> checkStatus(@Valid @RequestBody AccountStatusRequest request) {
        log.info("Account status check requested (identifier length={})", 
            request.getUser_identifier() != null ? request.getUser_identifier().length() : 0);
        AccountStatusResponse response = accountStatusService.checkStatus(request.getUser_identifier());
        return ResponseEntity.ok(response);
    }

    /**
     * Create a new account. Requires X-API-Key. At least one of email or phoneNumber must be provided.
     */
    @PostMapping(
        value = "",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Account> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Account created = accountStatusService.createAccount(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
