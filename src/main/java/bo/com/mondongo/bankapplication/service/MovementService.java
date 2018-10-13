package bo.com.mondongo.bankapplication.service;

import bo.com.mondongo.bankapplication.entity.Movement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service("MovementService")
public class MovementService {
    public ResponseEntity create(Movement movement) {
        Map<String, Object> response = new HashMap<>();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
