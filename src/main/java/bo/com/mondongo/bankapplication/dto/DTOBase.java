package bo.com.mondongo.bankapplication.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

class DTOBase {
    @JsonIgnore
    private final LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    private final LocalDateTime editedAt = LocalDateTime.now();

    @JsonIgnore
    private final boolean active = true;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public boolean isActive() {
        return active;
    }
}
