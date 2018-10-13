package bo.com.mondongo.bankapplication.service;

import bo.com.mondongo.bankapplication.entity.Account;
import bo.com.mondongo.bankapplication.entity.Currency;
import bo.com.mondongo.bankapplication.entity.Movement;
import bo.com.mondongo.bankapplication.repository.AccountRepository;
import bo.com.mondongo.bankapplication.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service("MovementService")
public class MovementService {
    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;

    @Autowired
    public MovementService(@Qualifier("AccountRepository") AccountRepository accountRepository,
                           @Qualifier("MovementRepository") MovementRepository movementRepository) {
        this.accountRepository = accountRepository;
        this.movementRepository = movementRepository;
    }

    public ResponseEntity create(Movement movement) {
        Account account = accountRepository.findOne(movement.getAccount().getId());
        movement.setAccount(account);
        if (movement.getCurrency().equals(account.getCurrency())) {
            account.setBalance(account.getBalance() + movement.getAmount());
        } else if (Currency.DOLLARS.equals(movement.getCurrency()) &&
            Currency.BOLIVIANOS.equals(account.getCurrency())) {
            account.setBalance(account.getBalance() + (movement.getAmount()*6.97));
        } else if (Currency.BOLIVIANOS.equals(movement.getCurrency()) &&
            Currency.DOLLARS.equals(account.getCurrency())) {
            account.setBalance(account.getBalance() + (movement.getAmount()/6.84));
        }
        accountRepository.save(account);
        movement = movementRepository.save(movement);
        Map<String, Object> response = new HashMap<>();
        response.put("id", movement.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
