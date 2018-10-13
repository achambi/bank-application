package bo.com.mondongo.bankapplication.repository;

import bo.com.mondongo.bankapplication.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.io.Serializable;
import java.util.List;

@Repository("AccountRepository")
public interface AccountRepository extends JpaRepository<Account, Serializable> {
    List<Account> findAll();
}
