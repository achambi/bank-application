package bo.com.mondongo.bankapplication.controller;

import bo.com.mondongo.bankapplication.dto.AccountDTO;
import bo.com.mondongo.bankapplication.dto.DTO;
import bo.com.mondongo.bankapplication.entity.Account;
import bo.com.mondongo.bankapplication.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@Api(value = "account module", description = "Operations pertaining to accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(@Qualifier("AccountService") AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "Create a account", response = ResponseEntity.class)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity createAccount(@Valid @DTO(AccountDTO.class) Account account) {
        return accountService.create(account);
    }

    @ApiOperation(value = "View a list of accounts", response = List.class)
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public List<AccountDTO> getAll() {
        return accountService.listAll();
    }
}
