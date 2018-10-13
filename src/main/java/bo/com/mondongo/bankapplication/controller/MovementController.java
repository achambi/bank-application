package bo.com.mondongo.bankapplication.controller;

import bo.com.mondongo.bankapplication.dto.DTO;
import bo.com.mondongo.bankapplication.dto.MovementInsertDto;
import bo.com.mondongo.bankapplication.entity.Movement;
import bo.com.mondongo.bankapplication.service.MovementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/movements")
@Api(value = "movement module", description = "Operations pertaining to account movements")
public class MovementController {
    private final MovementService movementService;

    @Autowired
    public MovementController(@Qualifier("MovementService") MovementService movementService) {
        this.movementService = movementService;
    }

    @ApiOperation(value = "Make a movement", response = ResponseEntity.class)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity createAccount(@Valid @DTO(MovementInsertDto.class) Movement movement) {
        if (movement.getAmount() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return movementService.create(movement);
    }
}


