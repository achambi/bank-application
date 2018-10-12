package bo.com.mondongo.bankapplication.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@SuppressWarnings("unused")
@Getter
@Setter
class DTOBase {
    @JsonIgnore
    private final LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    private final LocalDateTime editedAt = LocalDateTime.now();

    @JsonIgnore
    private final boolean active = true;
}
