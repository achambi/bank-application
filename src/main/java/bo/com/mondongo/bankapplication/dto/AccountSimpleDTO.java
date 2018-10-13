package bo.com.mondongo.bankapplication.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
@Getter
@Setter
public class AccountSimpleDTO extends DTOBase implements Serializable {
    @ApiModelProperty(value = "Account correlative id")
    private Integer id;

    @NotNull
    @ApiModelProperty(value = "Account number", readOnly = true)
    @Size(min = 13, max = 13, message = "The length of the account must have 13 characters.")
    private String number;

    public AccountSimpleDTO(final Integer id, final String number) {
        this.id = id;
        this.number = number;
    }

    public AccountSimpleDTO() {
    }
}
