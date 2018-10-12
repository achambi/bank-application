package bo.com.mondongo.bankapplication.service;

import bo.com.mondongo.bankapplication.converter.AccountConverter;
import bo.com.mondongo.bankapplication.dto.AccountDTO;
import bo.com.mondongo.bankapplication.entity.Account;
import bo.com.mondongo.bankapplication.entity.Movement;
import bo.com.mondongo.bankapplication.repository.AccountRepository;
import bo.com.mondongo.bankapplication.repository.MovementRepository;
import javafx.beans.binding.When;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountConverter accountConverter;

    @Mock
    private MovementRepository movementRepository;

    @InjectMocks
    private AccountService accountService;

    @After
    public void tearDown() {
        verifyNoMoreInteractions(accountRepository, accountConverter, movementRepository);
    }

    @Test
    public void create() {
        Account account = new Account();
        account.setNumber("100-1-001");
        account.setBalance(0.00);
        account.setCurrency("bolivianos");
        account.setId(1);
        when(accountRepository.save(eq(account))).thenReturn(account);
        ResponseEntity responseEntity = accountService.create(account);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Map result = (Map) (responseEntity.getBody());
        assertEquals(account.getId(), result.get("id"));
        verify(accountRepository, times(1)).save(eq(account));
    }

    @Test
    public void create_wrongCase() {
        Account account = new Account();
        account.setNumber("100-1-001");
        account.setBalance(200.00);
        account.setCurrency("bolivianos");

        //noinspection unchecked
        when(accountRepository.save(eq(account))).thenThrow(DataIntegrityViolationException.class);

        ResponseEntity responseEntity = accountService.create(account);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(accountRepository, times(1)).save(eq(account));
    }

    @Test
    public void create_accountMovementCase() {
        Account account = new Account();
        account.setNumber("100-2-001");
        account.setBalance(100.00);
        account.setCurrency("dollars");
        account.setId(1);
        when(accountRepository.save(eq(account))).thenReturn(account);
        Movement movement = new Movement();
        movement.setId(1);
        movement.setCurrency("dollars");
        movement.setAccount(account);
        movement.setMovementType("deposit");
        movement.setAmount(100.00);
        when(movementRepository.save(refEq(movement, "id"))).thenReturn(movement);
        ResponseEntity responseEntity = accountService.create(account);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Map result = (Map) (responseEntity.getBody());
        assertEquals(account.getId(), result.get("id"));
        assertEquals(movement.getId(), result.get("movementId"));
        verify(accountRepository, times(1)).save(eq(account));
        verify(movementRepository, times(1)).save(refEq(movement, "id"));
    }

    @Test
    public void listAll() {
        List<Account> accountList = new ArrayList<>();
        when(accountRepository.findAll()).thenReturn(accountList);
        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(new AccountDTO(1, "10001-1", 200.00, "bolivianos"));
        when(accountConverter.FromAccountToAccountDto(eq(accountList))).thenReturn(accountDTOList);
        List<AccountDTO> accountResponseList = accountService.listAll();
        assertNotNull(accountResponseList);
        assertEquals(accountDTOList, accountResponseList);

        verify(accountRepository, times(1)).findAll();
        verify(accountConverter, times(1)).FromAccountToAccountDto(eq(accountList));
    }
}