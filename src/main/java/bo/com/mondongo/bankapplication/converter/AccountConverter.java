package bo.com.mondongo.bankapplication.converter;

import bo.com.mondongo.bankapplication.dto.AccountDTO;
import bo.com.mondongo.bankapplication.dto.AccountSimpleDTO;
import bo.com.mondongo.bankapplication.entity.Account;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component("AccountConverter")
public class AccountConverter {
    public List<AccountDTO> FromAccountToAccountDto(List<Account> accounts) {
        List<AccountDTO> accountList = new ArrayList<>();
        for (Account account : accounts) {
            accountList.add(
                new AccountDTO(account.getId(),
                               account.getNumber(),
                               account.getHolder(),
                               account.getDepartment(),
                               account.getBalance(),
                               account.getCurrency()
                ));
        }
        return accountList;
    }

    public List<AccountSimpleDTO> FromAccountToAccountSimpleDto(List<Account> accounts) {
        List<AccountSimpleDTO> accountList = new ArrayList<>();
        for (Account account : accounts) {
            accountList.add(new AccountSimpleDTO(account.getId(), account.getNumber()));
        }
        return accountList;
    }
}
