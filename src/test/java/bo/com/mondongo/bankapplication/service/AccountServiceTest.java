package bo.com.mondongo.bankapplication.service;

import bo.com.mondongo.bankapplication.converter.AccountConverter;
import bo.com.mondongo.bankapplication.dto.AccountDTO;
import bo.com.mondongo.bankapplication.dto.AccountSimpleDTO;
import bo.com.mondongo.bankapplication.entity.*;
import bo.com.mondongo.bankapplication.repository.AccountRepository;
import bo.com.mondongo.bankapplication.repository.MovementRepository;
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

    //region 1. create method
    @Test
    public void create() {
        Account account = new Account();
        account.setBalance(0.00);
        account.setCurrency(Currency.BOLIVIANOS);
        account.setHolder("Jhon Snow");
        account.setDepartment(Department.LA_PAZ);
        account.setId(1);
        when(accountRepository.save(eq(account))).thenReturn(account);
        ResponseEntity responseEntity = accountService.create(account);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Map result = (Map) (responseEntity.getBody());
        assertEquals(account.getId(), result.get("id"));
        assertEquals("201-01-000001", account.getNumber());
        verify(accountRepository, times(2)).save(eq(account));
    }

    /**
     * Method to verify if exists a problem when an account is saved return a valid HTTP RESPONSE CODE.
     */
    @Test
    public void create_CaseDataIntegrityViolationException() {
        Account account = new Account();
        account.setNumber("100-1-001");
        account.setBalance(0.00);
        account.setCurrency(Currency.BOLIVIANOS);

        //noinspection unchecked
        when(accountRepository.save(eq(account))).thenThrow(DataIntegrityViolationException.class);

        ResponseEntity responseEntity = accountService.create(account);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(accountRepository, times(1)).save(eq(account));
    }

    @Test
    public void create_accountWithMovementCase() {
        //Mock an account
        Account account = new Account();
        account.setNumber("");
        account.setBalance(100.00);
        account.setCurrency(Currency.DOLLARS);
        account.setDepartment(Department.BENI);
        account.setId(100);
        when(accountRepository.save(eq(account))).thenReturn(account);

        //MOCK a movement
        Movement movement = new Movement();
        movement.setId(100);
        movement.setCurrency(Currency.DOLLARS);
        movement.setAccount(account);
        movement.setMovementType(MovementType.CREDIT);
        movement.setAmount(100.00);

        //ref equals to return a movement object. (Note.- Java is not a POO language)
        when(movementRepository.save(refEq(movement, "id"))).thenReturn(movement);

        ResponseEntity responseEntity = accountService.create(account);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Map result = (Map) (responseEntity.getBody());
        assertEquals(account.getId(), result.get("id"));
        assertEquals("202-06-000100", account.getNumber());

        assertEquals(movement.getId(), result.get("movementId"));

        //We need to validate 2 times because we need the correlative id to create the account number.
        verify(accountRepository, times(2)).save(eq(account));
        //We need to validate only save a movement.
        verify(movementRepository, times(1)).save(refEq(movement, "id"));
    }

    //endregion

    @Test
    public void listAll() {
        List<Account> accountList = new ArrayList<>();
        when(accountRepository.findAll()).thenReturn(accountList);
        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(
            new AccountDTO(1, "201-01-000001", "Jhon Snow", Department.LA_PAZ, 200.00, Currency.BOLIVIANOS));
        when(accountConverter.FromAccountToAccountDto(eq(accountList))).thenReturn(accountDTOList);
        List<AccountDTO> accountResponseList = accountService.getAll();
        assertNotNull(accountResponseList);
        assertEquals(accountDTOList, accountResponseList);

        verify(accountRepository, times(1)).findAll();
        verify(accountConverter, times(1)).FromAccountToAccountDto(eq(accountList));
    }

    @Test
    public void getSimpleList() {
        List<Account> accountList = new ArrayList<>();
        when(accountRepository.findAll()).thenReturn(accountList);
        List<AccountSimpleDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(new AccountSimpleDTO(1, "10001-1"));
        when(accountConverter.FromAccountToAccountSimpleDto(eq(accountList))).thenReturn(accountDTOList);
        List<AccountSimpleDTO> accountResponseList = accountService.getSimpleList();
        assertNotNull(accountResponseList);
        assertEquals(accountDTOList, accountResponseList);

        verify(accountRepository, times(1)).findAll();
        verify(accountConverter, times(1)).FromAccountToAccountSimpleDto(eq(accountList));
    }
}