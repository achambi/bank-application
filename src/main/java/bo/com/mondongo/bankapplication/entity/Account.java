package bo.com.mondongo.bankapplication.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "account")
@ApiModel(value = "AccountDTO", description = "Account model for the documentation")
public class Account extends EntityBase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "number", length = 10, nullable = false, unique = true)
    private String number;

    @Column(name = "balance", nullable = false, precision = 10, scale = 2)
    private Double balance;

    @Column(name = "currency", length = 10, nullable = false)
    private String currency;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Movement> movements;

    public Integer getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Double getBalance() {
        return balance;
    }

    public String getCurrency() {
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

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public Set<Movement> getMovements() {
        return movements;
    }

    public void setMovements(Set<Movement> movements) {
        this.movements = movements;
    }
}
