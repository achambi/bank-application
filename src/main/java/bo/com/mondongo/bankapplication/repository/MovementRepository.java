package bo.com.mondongo.bankapplication.repository;

import bo.com.mondongo.bankapplication.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.io.Serializable;

@Repository("MovementRepository")
public interface MovementRepository extends JpaRepository<Movement, Serializable> {

}
