package bo.com.mondongo.bankapplication.dto;

import bo.com.mondongo.bankapplication.entity.Currency;
import bo.com.mondongo.bankapplication.entity.Department;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@SuppressWarnings("unused")
public class AccountDTO extends DTOBase implements Serializable {
    @ApiModelProperty(value = "Account correlative id")
    private Integer id;

    @NotNull
    @ApiModelProperty(value = "Account number", readOnly = true)
    @Size(min = 13, max = 13, message = "The length of the account must have 13 characters.")
    private String number;

    @NotNull
    @ApiModelProperty(required = true, value = "initial balance in an account")
    @Min(value = 0, message = "the balance of the account should be positive.")
    private Double balance;

    @NotNull
    @ApiModelProperty(required = true, value = "Department")
    private Department department;

    @NotNull
    @ApiModelProperty(required = true, value = "Account currency", allowableValues = "BOLIVIANOS, DOLLARS")
    private Currency currency;

    @NotNull
    @ApiModelProperty(required = true, value = "Account holder")
    @Size(max = 30, message = "The account holder must have a maximum of 30 characters.")
    private String holder;

    public AccountDTO(Integer id, String number, String holder, Department department, Double balance, Currency currency) {
        this.id = id;
        this.number = number;
        this.department = department;
        this.holder = holder;
        this.balance = balance;
        this.currency = currency;
    }

    public AccountDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
}
