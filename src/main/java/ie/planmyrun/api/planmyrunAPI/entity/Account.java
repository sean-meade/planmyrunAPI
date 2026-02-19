package ie.planmyrun.api.planmyrunAPI.entity;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * User account entity for lookups by email, phone, or account ID.
 * Used by the account status endpoint (e.g. Retell voice AI).
 */
@Entity
@Table(name = "account", indexes = {
    @Index(name = "idx_account_email", columnList = "email"),
    @Index(name = "idx_account_phone", columnList = "phone_number")
})
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status = AccountStatus.ACTIVE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    /** True if account exists and is not suspended or deleted. */
    public boolean isActive() {
        return status == AccountStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
