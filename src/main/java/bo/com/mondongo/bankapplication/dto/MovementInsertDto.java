package bo.com.mondongo.bankapplication.dto;

import bo.com.mondongo.bankapplication.entity.Currency;
import bo.com.mondongo.bankapplication.entity.Movement;
import bo.com.mondongo.bankapplication.entity.MovementType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MovementInsertDto {
    @NotNull
    @ApiModelProperty(required = true, value = "Account currency", allowableValues = "BOLIVIANOS, DOLLARS")
    private Currency currency;

    @ApiModelProperty(value = "Account correlative id", required = true)
    @NotNull
    @Min(value = 1, message = "Account id should be positive.")
    private Integer accountId;

    @NotNull
    @ApiModelProperty(value = "Movement type", required = true, allowableValues = "DEBIT, CREDIT")
    private MovementType movementType;

    @NotNull
    @ApiModelProperty(required = true, value = "Amount to debit or credit in an account")
    private Double amount;

    public MovementInsertDto(Currency currency, Integer accountId, MovementType movementType, Double amount) {
        this.currency = currency;
        this.accountId = accountId;
        this.movementType = movementType;
        this.amount = amount;
    }
}
