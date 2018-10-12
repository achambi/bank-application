package bo.com.mondongo.bankapplication.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@SuppressWarnings("unused")
@Getter
@Setter
public class AccountDTO extends DTOBase implements Serializable {

    private Integer id;

    @NotNull
    @ApiModelProperty(required = true, value = "Account number")
    @Size(min = 13, max = 13, message = "The length of the account must be 13 characters.")
    private String number;

    @NotNull
    private Double balance;

    @NotNull
    @ApiModelProperty(required = true, value = "Account currency", allowableValues = "BOLIVIANOS, DOLLARS")
    private String currency;

    public AccountDTO(Integer id, String number, Double balance, String currency) {
        this.id = id;
        this.number = number;
        this.balance = balance;
        this.currency = currency;
    }

    public AccountDTO(String number, Double balance, String currency) {
        this.number = number;
        this.balance = balance;
        this.currency = currency;
    }

    public AccountDTO() {
    }
}
