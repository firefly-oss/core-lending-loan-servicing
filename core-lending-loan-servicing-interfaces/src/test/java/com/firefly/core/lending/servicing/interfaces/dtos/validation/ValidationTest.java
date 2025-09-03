package com.firefly.core.lending.servicing.interfaces.dtos.validation;

import com.firefly.core.lending.servicing.interfaces.dtos.accrual.v1.LoanAccrualDTO;
import com.firefly.core.lending.servicing.interfaces.dtos.disbursement.v1.LoanDisbursementDTO;
import com.firefly.core.lending.servicing.interfaces.dtos.rate.v1.LoanRateChangeDTO;
import com.firefly.core.lending.servicing.interfaces.dtos.repayment.v1.LoanRepaymentRecordDTO;
import com.firefly.core.lending.servicing.interfaces.dtos.repayment.v1.LoanRepaymentScheduleDTO;
import com.firefly.core.lending.servicing.interfaces.dtos.servicing.v1.LoanServicingCaseDTO;
import com.firefly.core.lending.servicing.interfaces.dtos.servicing.v1.LoanServicingEventDTO;
import com.firefly.core.lending.servicing.interfaces.enums.accrual.v1.AccrualTypeEnum;
import com.firefly.core.lending.servicing.interfaces.enums.rate.v1.ReasonCodeEnum;
import com.firefly.core.lending.servicing.interfaces.enums.servicing.v1.EventTypeEnum;
import com.firefly.core.lending.servicing.interfaces.enums.servicing.v1.ServicingStatusEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to verify validation annotations are working correctly on all DTOs
 */
public class ValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testLoanAccrualDTO_ValidData_NoViolations() {
        LoanAccrualDTO dto = LoanAccrualDTO.builder()
                .loanServicingCaseId(UUID.randomUUID())
                .accrualAmount(new BigDecimal("100.50"))
                .accrualType(AccrualTypeEnum.INTEREST)
                .accrualDate(LocalDate.now())
                .note("Test note")
                .build();

        Set<ConstraintViolation<LoanAccrualDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testLoanAccrualDTO_InvalidData_HasViolations() {
        LoanAccrualDTO dto = LoanAccrualDTO.builder()
                .loanServicingCaseId(null) // Required field
                .accrualAmount(new BigDecimal("-10.00")) // Must be positive
                .accrualType(null) // Required field
                .accrualDate(LocalDate.now().plusDays(1)) // Cannot be in future
                .note("A".repeat(501)) // Exceeds max length
                .build();

        Set<ConstraintViolation<LoanAccrualDTO>> violations = validator.validate(dto);
        assertEquals(5, violations.size());
    }

    @Test
    void testLoanDisbursementDTO_ValidData_NoViolations() {
        LoanDisbursementDTO dto = LoanDisbursementDTO.builder()
                .loanServicingCaseId(UUID.randomUUID())
                .transactionId(UUID.randomUUID())
                .disbursementAmount(new BigDecimal("1000.00"))
                .disbursementDate(LocalDate.now())
                .isFinalDisbursement(true)
                .note("Test disbursement")
                .build();

        Set<ConstraintViolation<LoanDisbursementDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testLoanRateChangeDTO_InvalidInterestRates_HasViolations() {
        LoanRateChangeDTO dto = LoanRateChangeDTO.builder()
                .loanServicingCaseId(UUID.randomUUID())
                .oldInterestRate(new BigDecimal("150.0")) // Exceeds 100%
                .newInterestRate(new BigDecimal("-5.0")) // Negative rate
                .effectiveDate(LocalDate.now())
                .reasonCode(ReasonCodeEnum.INDEX_ADJUSTMENT)
                .build();

        Set<ConstraintViolation<LoanRateChangeDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
    }

    @Test
    void testLoanServicingCaseDTO_ValidData_NoViolations() {
        LoanServicingCaseDTO dto = LoanServicingCaseDTO.builder()
                .contractId(UUID.randomUUID())
                .productId(UUID.randomUUID())
                .accountId(UUID.randomUUID())
                .servicingStatus(ServicingStatusEnum.ACTIVE)
                .principalOutstanding(new BigDecimal("50000.00"))
                .interestOutstanding(new BigDecimal("1500.00"))
                .feesOutstanding(new BigDecimal("100.00"))
                .originationDate(LocalDate.now().minusYears(1))
                .maturityDate(LocalDate.now().plusYears(5))
                .remarks("Test loan case")
                .build();

        Set<ConstraintViolation<LoanServicingCaseDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testLoanServicingEventDTO_BlankDescription_HasViolation() {
        LoanServicingEventDTO dto = LoanServicingEventDTO.builder()
                .loanServicingCaseId(UUID.randomUUID())
                .eventType(EventTypeEnum.COLLECTION_CALL)
                .eventDate(LocalDate.now())
                .description("") // Blank description
                .build();

        Set<ConstraintViolation<LoanServicingEventDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Description is required"));
    }

    @Test
    void testLoanRepaymentScheduleDTO_InvalidInstallmentNumber_HasViolation() {
        LoanRepaymentScheduleDTO dto = LoanRepaymentScheduleDTO.builder()
                .loanServicingCaseId(UUID.randomUUID())
                .installmentNumber(0) // Must be at least 1
                .dueDate(LocalDate.now().plusMonths(1))
                .principalDue(new BigDecimal("1000.00"))
                .interestDue(new BigDecimal("50.00"))
                .feeDue(new BigDecimal("10.00"))
                .totalDue(new BigDecimal("1060.00"))
                .isPaid(false)
                .build();

        Set<ConstraintViolation<LoanRepaymentScheduleDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Installment number must be at least 1"));
    }
}
