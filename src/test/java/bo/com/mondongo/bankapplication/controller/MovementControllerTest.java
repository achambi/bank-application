package bo.com.mondongo.bankapplication.controller;

import bo.com.mondongo.bankapplication.dto.DTOModelMapper;
import bo.com.mondongo.bankapplication.dto.MovementInsertDto;
import bo.com.mondongo.bankapplication.entity.Currency;
import bo.com.mondongo.bankapplication.entity.Movement;
import bo.com.mondongo.bankapplication.entity.MovementType;
import bo.com.mondongo.bankapplication.service.MovementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import javax.ws.rs.core.MediaType;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class MovementControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private MovementService movementService;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
            .standaloneSetup(new MovementController(movementService))
            .setCustomArgumentResolvers(new DTOModelMapper(objectMapper))
            .build();
    }

    @Test
    public void createMovement() throws Exception {
        MovementInsertDto movementInsertDto = new MovementInsertDto(Currency.BOLIVIANOS,
                                                                    1,
                                                                    MovementType.DEBIT,
                                                                    -20.00
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/movements/")
                                              .content(objectMapper.writeValueAsBytes(movementInsertDto))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
        Mockito.verify(movementService).create(any(Movement.class));
    }

    @Test
    public void createMovement_CurrencyNull() throws Exception {
        MovementInsertDto movementInsertDto = new MovementInsertDto(null,
                                                                    1,
                                                                    MovementType.DEBIT,
                                                                    -20.00
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/movements/")
                                              .content(objectMapper.writeValueAsBytes(movementInsertDto))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());
    }

    @Test
    public void createMovement_MovementTypeNull() throws Exception {
        MovementInsertDto movementInsertDto = new MovementInsertDto(Currency.DOLLARS,
                                                                    1,
                                                                    null,
                                                                    -20.00
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/movements/")
                                              .content(objectMapper.writeValueAsBytes(movementInsertDto))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());
    }

    @Test
    public void createMovement_AccountIdZero() throws Exception {
        MovementInsertDto movementInsertDto = new MovementInsertDto(Currency.DOLLARS,
                                                                    0,
                                                                    MovementType.DEBIT,
                                                                    -20.00
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/movements/")
                                              .content(objectMapper.writeValueAsBytes(movementInsertDto))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());
    }

    @Test
    public void createMovement_AccountIdNull() throws Exception {
        MovementInsertDto movementInsertDto = new MovementInsertDto(Currency.DOLLARS,
                                                                    null,
                                                                    MovementType.DEBIT,
                                                                    -20.00
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/movements/")
                                              .content(objectMapper.writeValueAsBytes(movementInsertDto))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());
    }

    @Test
    public void createMovement_AmountNull() throws Exception {
        MovementInsertDto movementInsertDto = new MovementInsertDto(Currency.DOLLARS,
                                                                    1,
                                                                    MovementType.DEBIT,
                                                                    null
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/movements/")
                                              .content(objectMapper.writeValueAsBytes(movementInsertDto))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());
    }

    @Test
    public void createMovement_AmountZero() throws Exception {
        MovementInsertDto movementInsertDto = new MovementInsertDto(Currency.DOLLARS,
                                                                    1,
                                                                    MovementType.DEBIT,
                                                                    0.00
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/movements/")
                                              .content(objectMapper.writeValueAsBytes(movementInsertDto))
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError());
    }
}
