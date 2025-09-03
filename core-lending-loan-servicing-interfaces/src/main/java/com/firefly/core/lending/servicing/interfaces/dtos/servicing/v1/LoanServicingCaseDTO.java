package com.firefly.core.lending.servicing.interfaces.dtos.servicing.v1;

import com.firefly.core.lending.servicing.interfaces.enums.servicing.v1.ServicingStatusEnum;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanServicingCaseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID loanServicingCaseId;

    @FilterableId
    @NotNull(message = "Contract ID is required")
    private UUID contractId;

    @FilterableId
    @NotNull(message = "Product ID is required")
    private UUID productId;

    @FilterableId
    @NotNull(message = "Account ID is required")
    private UUID accountId;

    @NotNull(message = "Servicing status is required")
    private ServicingStatusEnum servicingStatus;

    @NotNull(message = "Principal outstanding is required")
    @DecimalMin(value = "0.0", message = "Principal outstanding cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Principal outstanding must have at most 15 integer digits and 2 decimal places")
    private BigDecimal principalOutstanding;

    @NotNull(message = "Interest outstanding is required")
    @DecimalMin(value = "0.0", message = "Interest outstanding cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Interest outstanding must have at most 15 integer digits and 2 decimal places")
    private BigDecimal interestOutstanding;

    @NotNull(message = "Fees outstanding is required")
    @DecimalMin(value = "0.0", message = "Fees outstanding cannot be negative")
    @Digits(integer = 15, fraction = 2, message = "Fees outstanding must have at most 15 integer digits and 2 decimal places")
    private BigDecimal feesOutstanding;

    @NotNull(message = "Origination date is required")
    @PastOrPresent(message = "Origination date cannot be in the future")
    private LocalDate originationDate;

    @NotNull(message = "Maturity date is required")
    @Future(message = "Maturity date must be in the future")
    private LocalDate maturityDate;

    @Size(max = 1000, message = "Remarks cannot exceed 1000 characters")
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}