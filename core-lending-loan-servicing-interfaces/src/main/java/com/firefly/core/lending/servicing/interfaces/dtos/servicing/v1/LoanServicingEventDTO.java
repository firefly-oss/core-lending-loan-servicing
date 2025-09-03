package com.firefly.core.lending.servicing.interfaces.dtos.servicing.v1;

import com.firefly.core.lending.servicing.interfaces.enums.servicing.v1.EventTypeEnum;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanServicingEventDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID loanServicingEventId;

    @FilterableId
    @NotNull(message = "Loan servicing case ID is required")
    private UUID loanServicingCaseId;

    @NotNull(message = "Event type is required")
    private EventTypeEnum eventType;

    @NotNull(message = "Event date is required")
    @PastOrPresent(message = "Event date cannot be in the future")
    private LocalDate eventDate;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

