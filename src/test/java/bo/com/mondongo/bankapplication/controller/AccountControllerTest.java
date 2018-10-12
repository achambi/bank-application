package bo.com.mondongo.bankapplication.controller;

import bo.com.mondongo.bankapplication.dto.AccountDTO;
import bo.com.mondongo.bankapplication.dto.DTOModelMapper;
import bo.com.mondongo.bankapplication.entity.Account;
import bo.com.mondongo.bankapplication.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import javax.persistence.EntityManager;
import javax.ws.rs.core.MediaType;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest extends TestCase {

    private AccountController accountController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private AccountService accountService;

    @Mock
    private EntityManager entityManager;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();

        accountController = new AccountController(accountService);
        mockMvc = MockMvcBuilders
            .standaloneSetup(accountController)
            .setCustomArgumentResolvers(new DTOModelMapper(objectMapper, entityManager))
            .build();
    }

    @Test
    public void createAccount() throws Exception {
        AccountDTO accountDTO = new AccountDTO("100-1-100", 20.00, "bolivianos");

        mockMvc.perform(post("/accounts/")
                            .content(objectMapper.writeValueAsBytes(accountDTO))
                            .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        Mockito.verify(accountService).create(any(Account.class));
    }

    @Test
    public void createAccount_ValidateAccountLength() throws Exception {
        AccountDTO accountDTO = new AccountDTO("100-1-100", 20.00, "bolivianos");

        mockMvc.perform(post("/accounts/")
                            .content(objectMapper.writeValueAsBytes(accountDTO))
                            .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());
    }
}