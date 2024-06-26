package bo.com.mondongo.bankapplication.service;

import bo.com.mondongo.bankapplication.entity.*;
import bo.com.mondongo.bankapplication.repository.AccountRepository;
import bo.com.mondongo.bankapplication.repository.MovementRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovementServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MovementRepository movementRepository;

    @InjectMocks
    private MovementService movementService;

    @After
    public void tearDown() {
        verifyNoMoreInteractions(accountRepository, movementRepository);
    }

    //region 1. DEBIT CASES
    @Test
    public void create_CaseDebit_MovBolivianosAndAccountBolivianos() {
        Account account = new Account();
        account.setBalance(100.00);
        account.setCurrency(Currency.BOLIVIANOS);
        account.setHolder("Jhon Snow");
        account.setDepartment(Department.LA_PAZ);
        account.setId(1);
        account.createAccountNumber();

        Movement movement = new Movement();
        movement.setAccount(account);
        movement.setAmount(-100.00);
        movement.setCurrency(Currency.BOLIVIANOS);
        movement.setMovementType(MovementType.DEBIT);
        movement.setId(1);

        when(accountRepository.findOne(Matchers.eq(account.getId()))).thenReturn(account);
        when(movementRepository.save(Matchers.refEq(movement, "id"))).thenReturn(movement);

        ResponseEntity responseEntity = movementService.create(movement);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Map result = (Map) (responseEntity.getBody());
        assertEquals(movement.getId(), result.get("id"));

        assertEquals(0.00, account.getBalance(), 0.00);

        verify(accountRepository, times(1)).findOne(eq(account.getId()));
        verify(movementRepository, times(1)).save(eq(movement));
        verify(accountRepository, times(1)).save(eq(account));
    }

    @Test
    public void create_CaseDebit_MovDollarsAndAccountBolivianos() {
        Account account = new Account();
        account.setBalance(697.00);
        account.setCurrency(Currency.BOLIVIANOS);
        account.setHolder("Jhon Snow");
        account.setDepartment(Department.LA_PAZ);
        account.setId(1);
        account.createAccountNumber();

        Movement movement = new Movement();
        movement.setAccount(account);
        movement.setAmount(-100.00);
        movement.setCurrency(Currency.DOLLARS);
        movement.setMovementType(MovementType.DEBIT);
        movement.setId(1);

        when(accountRepository.findOne(Matchers.eq(account.getId()))).thenReturn(account);
        when(movementRepository.save(Matchers.refEq(movement, "id"))).thenReturn(movement);

        ResponseEntity responseEntity = movementService.create(movement);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Map result = (Map) (responseEntity.getBody());
        assertEquals(movement.getId(), result.get("id"));

        assertEquals(0.00, account.getBalance(), 0.00);

        verify(accountRepository, times(1)).findOne(eq(account.getId()));
        verify(movementRepository, times(1)).save(eq(movement));
        verify(accountRepository, times(1)).save(eq(account));
    }

    @Test
    public void create_CaseDebit_MovBolivianosAndAccountDollars() {
        Account account = new Account();
        account.setBalance(100.00);
        account.setCurrency(Currency.DOLLARS);
        account.setHolder("Jhon Snow");
        account.setDepartment(Department.LA_PAZ);
        account.setId(1);
        account.createAccountNumber();

        Movement movement = new Movement();
        movement.setAccount(account);
        movement.setAmount(-684.00);
        movement.setCurrency(Currency.BOLIVIANOS);
        movement.setMovementType(MovementType.DEBIT);
        movement.setId(1);

        when(accountRepository.findOne(Matchers.eq(account.getId()))).thenReturn(account);
        when(movementRepository.save(Matchers.refEq(movement, "id"))).thenReturn(movement);

        ResponseEntity responseEntity = movementService.create(movement);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Map result = (Map) (responseEntity.getBody());
        assertEquals(movement.getId(), result.get("id"));

        assertEquals(0.00, account.getBalance(), 0.00);

        verify(accountRepository, times(1)).findOne(eq(account.getId()));
        verify(movementRepository, times(1)).save(eq(movement));
        verify(accountRepository, times(1)).save(eq(account));
    }

    @Test
    public void create_CaseDebit_MovDollarsAndAccountDollars() {
        Account account = new Account();
        account.setBalance(200.00);
        account.setCurrency(Currency.DOLLARS);
        account.setHolder("Jhon Snow");
        account.setDepartment(Department.LA_PAZ);
        account.setId(1);
        account.createAccountNumber();

        Movement movement = new Movement();
        movement.setAccount(account);
        movement.setAmount(-100.00);
        movement.setCurrency(Currency.DOLLARS);
        movement.setMovementType(MovementType.DEBIT);
        movement.setId(1);

        when(accountRepository.findOne(Matchers.eq(account.getId()))).thenReturn(account);
        when(movementRepository.save(Matchers.refEq(movement, "id"))).thenReturn(movement);

        ResponseEntity responseEntity = movementService.create(movement);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Map result = (Map) (responseEntity.getBody());
        assertEquals(movement.getId(), result.get("id"));

        assertEquals(100.00, account.getBalance(), 0.00);

        verify(accountRepository, times(1)).findOne(eq(account.getId()));
        verify(movementRepository, times(1)).save(eq(movement));
        verify(accountRepository, times(1)).save(eq(account));
    }

    //endregion

    //region 2. CREDIT CASES
    @Test
    public void create_CaseCredit_MovBolivianosAndAccountBolivianos() {
        Account account = new Account();
        account.setBalance(100.00);
        account.setCurrency(Currency.BOLIVIANOS);
        account.setHolder("Jhon Snow");
        account.setDepartment(Department.LA_PAZ);
        account.setId(1);
        account.createAccountNumber();

        Movement movement = new Movement();
        movement.setAccount(account);
        movement.setAmount(100.00);
        movement.setCurrency(Currency.BOLIVIANOS);
        movement.setMovementType(MovementType.CREDIT);
        movement.setId(1);

        when(accountRepository.findOne(Matchers.eq(account.getId()))).thenReturn(account);
        when(movementRepository.save(Matchers.refEq(movement, "id"))).thenReturn(movement);

        ResponseEntity responseEntity = movementService.create(movement);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Map result = (Map) (responseEntity.getBody());
        assertEquals(movement.getId(), result.get("id"));

        assertEquals(200.00, account.getBalance(), 0.00);

        verify(accountRepository, times(1)).findOne(eq(account.getId()));
        verify(movementRepository, times(1)).save(eq(movement));
        verify(accountRepository, times(1)).save(eq(account));
    }

    @Test
    public void create_CaseCredit_MovDollarsAndAccountBolivianos() {
        Account account = new Account();
        account.setBalance(100.00);
        account.setCurrency(Currency.BOLIVIANOS);
        account.setHolder("Jhon Snow");
        account.setDepartment(Department.LA_PAZ);
        account.setId(1);
        account.createAccountNumber();

        Movement movement = new Movement();
        movement.setAccount(account);
        movement.setAmount(100.00);
        movement.setCurrency(Currency.DOLLARS);
        movement.setMovementType(MovementType.CREDIT);
        movement.setId(1);

        when(accountRepository.findOne(Matchers.eq(account.getId()))).thenReturn(account);
        when(movementRepository.save(Matchers.refEq(movement, "id"))).thenReturn(movement);

        ResponseEntity responseEntity = movementService.create(movement);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Map result = (Map) (responseEntity.getBody());
        assertEquals(movement.getId(), result.get("id"));

        assertEquals(784.00, account.getBalance(), 0.00);

        verify(accountRepository, times(1)).findOne(eq(account.getId()));
        verify(movementRepository, times(1)).save(eq(movement));
        verify(accountRepository, times(1)).save(eq(account));
    }

    @Test
    public void create_CaseCredit_MovBolivianosAndAccountDollars() {
        Account account = new Account();
        account.setBalance(100.00);
        account.setCurrency(Currency.DOLLARS);
        account.setHolder("Jhon Snow");
        account.setDepartment(Department.LA_PAZ);
        account.setId(1);
        account.createAccountNumber();

        Movement movement = new Movement();
        movement.setAccount(account);
        movement.setAmount(697.00);
        movement.setCurrency(Currency.BOLIVIANOS);
        movement.setMovementType(MovementType.CREDIT);
        movement.setId(1);

        when(accountRepository.findOne(Matchers.eq(account.getId()))).thenReturn(account);
        when(movementRepository.save(Matchers.refEq(movement, "id"))).thenReturn(movement);

        ResponseEntity responseEntity = movementService.create(movement);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Map result = (Map) (responseEntity.getBody());
        assertEquals(movement.getId(), result.get("id"));

        assertEquals(200.00, account.getBalance(), 0.00);

        verify(accountRepository, times(1)).findOne(eq(account.getId()));
        verify(movementRepository, times(1)).save(eq(movement));
        verify(accountRepository, times(1)).save(eq(account));
    }

    @Test
    public void create_CaseCredit_MovDollarsAndAccountDollars() {
        Account account = new Account();
        account.setBalance(200.00);
        account.setCurrency(Currency.DOLLARS);
        account.setHolder("Jhon Snow");
        account.setDepartment(Department.LA_PAZ);
        account.setId(1);
        account.createAccountNumber();

        Movement movement = new Movement();
        movement.setAccount(account);
        movement.setAmount(100.00);
        movement.setCurrency(Currency.DOLLARS);
        movement.setMovementType(MovementType.CREDIT);
        movement.setId(1);

        when(accountRepository.findOne(Matchers.eq(account.getId()))).thenReturn(account);
        when(movementRepository.save(Matchers.refEq(movement, "id"))).thenReturn(movement);

        ResponseEntity responseEntity = movementService.create(movement);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Map result = (Map) (responseEntity.getBody());
        assertEquals(movement.getId(), result.get("id"));

        assertEquals(300.00, account.getBalance(), 0.00);

        verify(accountRepository, times(1)).findOne(eq(account.getId()));
        verify(movementRepository, times(1)).save(eq(movement));
        verify(accountRepository, times(1)).save(eq(account));
    }

    //endregion
}