package bo.com.mondongo.bankapplication.service;

import bo.com.mondongo.bankapplication.converter.AccountConverter;
import bo.com.mondongo.bankapplication.dto.AccountDTO;
import bo.com.mondongo.bankapplication.entity.Account;
import bo.com.mondongo.bankapplication.entity.Movement;
import bo.com.mondongo.bankapplication.repository.AccountRepository;
import bo.com.mondongo.bankapplication.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("AccountService")
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountConverter accountConverter;
    private final MovementRepository movementRepository;

    @Autowired
    public AccountService(@Qualifier("AccountRepository") AccountRepository accountRepository,
                          @Qualifier("AccountConverter") AccountConverter accountConverter,
                          @Qualifier("MovementRepository") MovementRepository movementRepository) {
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
        this.movementRepository = movementRepository;
    }

    public ResponseEntity create(Account account) throws DataIntegrityViolationException {
        try {
            account = accountRepository.save(account);
            Map<String, Object> response = new HashMap<>();

            if (account.getBalance() > 0) {
                Movement movement = new Movement();
                movement.setAccount(account);
                movement.setAmount(account.getBalance());
                movement.setCurrency(account.getCurrency());
                movement.setMovementType("deposit");
                movement = movementRepository.save(movement);
                response.put("movementId", movement.getId());
            }

            response.put("id", account.getId());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public List<AccountDTO> listAll() {
        return accountConverter.FromAccountToAccountDto(accountRepository.findAll());
    }
}
