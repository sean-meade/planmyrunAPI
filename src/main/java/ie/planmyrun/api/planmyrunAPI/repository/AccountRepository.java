package ie.planmyrun.api.planmyrunAPI.repository;

import ie.planmyrun.api.planmyrunAPI.entity.Account;
import ie.planmyrun.api.planmyrunAPI.entity.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmailIgnoreCase(String email);

    Optional<Account> findByPhoneNumber(String phoneNumber);
}
