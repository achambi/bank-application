package bo.com.mondongo.bankapplication.dto;

import bo.com.mondongo.bankapplication.entity.Currency;
import bo.com.mondongo.bankapplication.entity.Department;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@SuppressWarnings("unused")
public class AccountInsertDTO extends DTOBase implements Serializable {
    @NotNull
    @ApiModelProperty(required = true, value = "initial balance in an account")
    @Min(value = 0, message = "the balance of the account should be positive.")
    private Double balance;

    @NotNull
    @ApiModelProperty(required = true, value = "Account currency", allowableValues = "BOLIVIANOS, DOLLARS")
    private Currency currency;

    @NotNull
    @ApiModelProperty(required = true, value = "Account holder")
    @Size(max = 30, message = "The account holder must have a maximum of 30 characters.")
    private String holder;

    @NotNull
    @ApiModelProperty(required = true, value = "Department")
    private Department department;

    public AccountInsertDTO(String holder, Double balance, Department department, Currency currency) {
        this.holder = holder;
        this.balance = balance;
        this.department = department;
        this.currency = currency;
    }

    public AccountInsertDTO() {
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
