package bo.com.mondongo.bankapplication.controller;

import bo.com.mondongo.bankapplication.dto.*;
import bo.com.mondongo.bankapplication.entity.Account;
import bo.com.mondongo.bankapplication.entity.Currency;
import bo.com.mondongo.bankapplication.entity.Department;
import bo.com.mondongo.bankapplication.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest extends TestCase {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private AccountService accountService;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
            .standaloneSetup(new AccountController(accountService))
            .setCustomArgumentResolvers(new DTOModelMapper(objectMapper))
            .build();
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(accountService);
    }

    //region 1. create accounts
    @Test
    public void createAccount() throws Exception {
        AccountInsertDTO accountInsertDTO = new AccountInsertDTO("Daenerys Targaryen",
                                                                 20.00,
                                                                 Department.BENI,
                                                                 Currency.BOLIVIANOS
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/")
                                              .content(objectMapper.writeValueAsBytes(accountInsertDTO))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        Mockito.verify(accountService).create(any(Account.class));
    }

    @Test
    public void createAccount_ValidateBalanceZero() throws Exception {
        AccountInsertDTO accountInsertDTO = new AccountInsertDTO("Daenerys Targaryen",
                                                                 0.00,
                                                                 Department.CHUQUISACA,
                                                                 Currency.BOLIVIANOS
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/")
                                              .content(objectMapper.writeValueAsBytes(accountInsertDTO))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        Mockito.verify(accountService).create(any(Account.class));
    }

    @Test
    public void createAccount_ValidateNullBalance() throws Exception {
        AccountInsertDTO accountInsertDTO = new AccountInsertDTO("Daenerys Targaryen",
                                                                 null,
                                                                 Department.CHUQUISACA,
                                                                 Currency.BOLIVIANOS
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/")
                                              .content(objectMapper.writeValueAsBytes(accountInsertDTO))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());
    }

    @Test
    public void createAccount_ValidateNegativeBalance() throws Exception {
        AccountInsertDTO accountInsertDTO = new AccountInsertDTO(
            "Daenerys Targaryen",
            -20.00,
            Department.LA_PAZ,
            Currency.BOLIVIANOS
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/")
                                              .content(objectMapper.writeValueAsBytes(accountInsertDTO))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());
    }

    @Test
    public void createAccount_ValidateDepartmentNull() throws Exception {
        AccountInsertDTO accountInsertDTO = new AccountInsertDTO(
            "Daenerys Targaryen",
            20.00,
            null,
            Currency.BOLIVIANOS
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/")
                                              .content(objectMapper.writeValueAsBytes(accountInsertDTO))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());
    }

    @Test
    public void createAccount_ValidateCurrency() throws Exception {
        AccountInsertDTO accountInsertDTO = new AccountInsertDTO(
            "Daenerys Targaryen",
            20.00,
            Department.CHUQUISACA,
            null
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/")
                                              .content(objectMapper.writeValueAsBytes(accountInsertDTO))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());
    }
    //endregion

    //region 2. update accounts
    @Test
    public void updateAccount() throws Exception {
        AccountUpdateDTO accountUpdateDTO = new AccountUpdateDTO(1,
                                                                 "Daenerys Targaryen Updated",
                                                                 20.00,
                                                                 Department.BENI,
                                                                 Currency.BOLIVIANOS
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/accounts/")
                                              .content(objectMapper.writeValueAsBytes(accountUpdateDTO))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        Mockito.verify(accountService).update(any(Account.class));
    }

    @Test
    public void updateAccount_CaseNullId() throws Exception {
        AccountUpdateDTO accountUpdateDTO = new AccountUpdateDTO(null,
                                                                 "Daenerys Targaryen Updated",
                                                                 20.00,
                                                                 Department.BENI,
                                                                 Currency.BOLIVIANOS
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/accounts/")
                                              .content(objectMapper.writeValueAsBytes(accountUpdateDTO))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateAccount_CaseIdZero() throws Exception {
        AccountUpdateDTO accountUpdateDTO = new AccountUpdateDTO(0,
                                                                 "Daenerys Targaryen Updated",
                                                                 20.00,
                                                                 Department.BENI,
                                                                 Currency.BOLIVIANOS
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/accounts/")
                                              .content(objectMapper.writeValueAsBytes(accountUpdateDTO))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateAccount_CaseNegativeId() throws Exception {
        AccountUpdateDTO accountUpdateDTO = new AccountUpdateDTO(-1,
                                                                 "Daenerys Targaryen Updated",
                                                                 20.00,
                                                                 Department.BENI,
                                                                 Currency.BOLIVIANOS
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/accounts/")
                                              .content(objectMapper.writeValueAsBytes(accountUpdateDTO))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());
    }

    //endregion

    @Test
    public void getAll() throws Exception {
        List<AccountDTO> accounts = Arrays.asList(
            new AccountDTO(1, "201-02-000001", "Daenerys Targaryen", Department.ORURO, 100.00, Currency.BOLIVIANOS),
            new AccountDTO(2, "202-02-000002", "Robert Baratheon", Department.ORURO, 200.00, Currency.DOLLARS),
            new AccountDTO(3, "201-09-000003", "Jhon Snow", Department.CHUQUISACA, 300.00, Currency.DOLLARS)
        );

        when(accountService.getAll()).thenReturn(accounts);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON_UTF8))
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].number", Matchers.is("201-02-000001")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].department", Matchers.is("ORURO")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].holder", Matchers.is("Daenerys Targaryen")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].balance", Matchers.is(100.00)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].currency", Matchers.is("BOLIVIANOS")))

               .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].number", Matchers.is("202-02-000002")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].department", Matchers.is("ORURO")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].holder", Matchers.is("Robert Baratheon")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].balance", Matchers.is(200.00)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].currency", Matchers.is("DOLLARS")))

               .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[2].number", Matchers.is("201-09-000003")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[2].department", Matchers.is("CHUQUISACA")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[2].holder", Matchers.is("Jhon Snow")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[2].balance", Matchers.is(300.00)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[2].currency", Matchers.is("DOLLARS")));
        Mockito.verify(accountService).getAll();
    }

    @Test
    public void getSimpleList() throws Exception {
        List<AccountSimpleDTO> accounts = Arrays.asList(
            new AccountSimpleDTO(1, "201-02-000001"),
            new AccountSimpleDTO(2, "202-02-000002"),
            new AccountSimpleDTO(3, "201-09-000003")
        );

        when(accountService.getSimpleList()).thenReturn(accounts);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/simple/list"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON_UTF8))
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].number", Matchers.is("201-02-000001")))

               .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].number", Matchers.is("202-02-000002")))

               .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[2].number", Matchers.is("201-09-000003")));
        Mockito.verify(accountService).getSimpleList();
    }
}