package ie.planmyrun.api.planmyrunAPI.controller;

import ie.planmyrun.api.planmyrunAPI.entity.Account;
import ie.planmyrun.api.planmyrunAPI.entity.AccountStatus;
import ie.planmyrun.api.planmyrunAPI.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountStatusControllerTest {

    private static final String API_KEY_HEADER = "X-API-Key";
    private static final String VALID_API_KEY = "test-api-key";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository.deleteAll();
    }

    @Test
    void checkStatus_returns401_whenApiKeyMissing() throws Exception {
        mockMvc.perform(post("/api/account/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"user_identifier\": \"user@example.com\"}"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void checkStatus_returns401_whenApiKeyInvalid() throws Exception {
        mockMvc.perform(post("/api/account/status")
                .header(API_KEY_HEADER, "wrong-key")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"user_identifier\": \"user@example.com\"}"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void checkStatus_returns400_whenUserIdentifierMissing() throws Exception {
        mockMvc.perform(post("/api/account/status")
                .header(API_KEY_HEADER, VALID_API_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(containsString("user_identifier")));
    }

    @Test
    void checkStatus_returns404_whenAccountNotFound() throws Exception {
        mockMvc.perform(post("/api/account/status")
                .header(API_KEY_HEADER, VALID_API_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"user_identifier\": \"nobody@example.com\"}"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value("inactive"))
            .andExpect(jsonPath("$.message").value("No account found for the given identifier."));
    }

    @Test
    void checkStatus_returns200_active_whenAccountExistsAndActive() throws Exception {
        Account account = new Account();
        account.setEmail("user@example.com");
        account.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);

        mockMvc.perform(post("/api/account/status")
                .header(API_KEY_HEADER, VALID_API_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"user_identifier\": \"user@example.com\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.active").value(true))
            .andExpect(jsonPath("$.status").value("active"))
            .andExpect(jsonPath("$.message").value("Account in good standing."));
    }

    @Test
    void checkStatus_returns200_inactive_whenAccountSuspended() throws Exception {
        Account account = new Account();
        account.setEmail("suspended@example.com");
        account.setStatus(AccountStatus.SUSPENDED);
        accountRepository.save(account);

        mockMvc.perform(post("/api/account/status")
                .header(API_KEY_HEADER, VALID_API_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"user_identifier\": \"suspended@example.com\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.active").value(false))
            .andExpect(jsonPath("$.status").value("suspended"));
    }

    @Test
    void checkStatus_findsByAccountId() throws Exception {
        Account account = new Account();
        account.setEmail("iduser@example.com");
        account.setStatus(AccountStatus.ACTIVE);
        account = accountRepository.save(account);

        mockMvc.perform(post("/api/account/status")
                .header(API_KEY_HEADER, VALID_API_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"user_identifier\": \"" + account.getId() + "\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.active").value(true));
    }
}
