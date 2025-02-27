package com.catalis.core.lending.servicing.interfaces.dtos.servicing.v1;

import com.catalis.common.core.filters.FilterableId;
import com.catalis.core.lending.servicing.interfaces.enums.servicing.v1.EventTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanServicingEventDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long loanServicingEventId;

    @FilterableId
    private Long loanServicingCaseId;

    private EventTypeEnum eventType;
    private LocalDate eventDate;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

