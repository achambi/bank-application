package bo.com.mondongo.bankapplication.dto;

import bo.com.mondongo.bankapplication.entity.Currency;
import bo.com.mondongo.bankapplication.entity.MovementType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@SuppressWarnings("unused")
public class MovementInsertDto extends DTOBase implements Serializable {
    @JsonIgnore
    private Integer id = null;

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

    public MovementInsertDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
