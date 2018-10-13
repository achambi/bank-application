package bo.com.mondongo.bankapplication.dto;

import bo.com.mondongo.bankapplication.entity.Currency;
import bo.com.mondongo.bankapplication.entity.Department;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
@Getter
@Setter
public class AccountUpdateDTO extends AccountInsertDTO implements Serializable {
    @ApiModelProperty(value = "Account correlative id", required = true)
    @NotNull
    @Min(value = 1, message = "the balance of the account should be positive.")
    @Id
    private Integer id;

    public AccountUpdateDTO(Integer id, String holder, Double balance, Department department, Currency currency) {
        super(holder, balance, department, currency);
        this.id = id;
    }

    public AccountUpdateDTO() {
    }
}
