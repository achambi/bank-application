package bo.com.mondongo.bankapplication.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "account")
@ApiModel(value = "AccountDTO", description = "Account model for the documentation")
public class Account extends EntityBase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "number", length = 13, unique = true)
    private String number;

    @Column(name = "balance", nullable = false, precision = 10, scale = 2)
    private Double balance;

    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "holder", length = 30, nullable = false)
    private String holder;

    @Column(name = "department", nullable = false)
    @Enumerated(EnumType.STRING)
    private Department department;

//    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
//    private List<Movement> movements;

    public Integer getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Double getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public void setBalance(final Double balance) {
        this.balance = balance;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

//    public List<Movement> getMovements() {
//        return movements;
//    }
//
//    public void setMovements(List<Movement> movements) {
//        this.movements = movements;
//    }

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

    public void createAccountNumber() {
        this.number = this.getCurrency().getValue() + "-" +
            this.getDepartment().getValue() + "-" +
            String.format("%06d", this.id);
    }
}
