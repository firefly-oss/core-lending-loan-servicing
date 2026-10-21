/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.fireflysoftware.core.lending.servicing.core.schedule;

import io.fireflysoftware.core.lending.servicing.interfaces.enums.AmortizationMethodEnum;
import io.fireflysoftware.core.lending.servicing.interfaces.enums.PaymentFrequencyEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Builds the installment rows the servicing core stores for one loan.
 */
public final class AmortizationCalendarCalculator {

    private static final int MONEY_SCALE = 2;
    private static final int RATE_SCALE = 12;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

    private AmortizationCalendarCalculator() {
    }

    public record Installment(
            int number,
            LocalDate dueDate,
            BigDecimal principalDue,
            BigDecimal interestDue,
            BigDecimal feeDue,
            BigDecimal totalDue,
            BigDecimal remainingBalance
    ) {
    }

    public static List<Installment> generate(
            BigDecimal principal,
            BigDecimal annualRatePercent,
            int term,
            PaymentFrequencyEnum paymentFrequency,
            AmortizationMethodEnum amortizationMethod,
            LocalDate firstDueDate,
            BigDecimal installmentAmount) {
        if (principal == null || principal.signum() <= 0) {
            throw new IllegalArgumentException("Principal amount is required");
        }
        if (annualRatePercent == null || annualRatePercent.signum() < 0) {
            throw new IllegalArgumentException("Interest rate is required");
        }
        if (term < 1) {
            throw new IllegalArgumentException("Loan term is required");
        }
        if (firstDueDate == null) {
            throw new IllegalArgumentException("First due date is required");
        }
        PaymentFrequencyEnum frequency = paymentFrequency == null ? PaymentFrequencyEnum.MONTHLY : paymentFrequency;
        BigDecimal periodicRate = annualRatePercent
                .divide(BigDecimal.valueOf(100), RATE_SCALE, ROUNDING)
                .divide(BigDecimal.valueOf(periodsPerYear(frequency)), RATE_SCALE, ROUNDING);
        AmortizationMethodEnum method = amortizationMethod == null
                ? AmortizationMethodEnum.EQUAL_INSTALLMENT
                : amortizationMethod;
        BigDecimal priced = installmentAmount == null ? null : money(installmentAmount);
        return switch (method) {
            case EQUAL_PRINCIPAL -> equalPrincipal(principal, periodicRate, term, frequency, firstDueDate);
            case INTEREST_ONLY, BULLET -> interestOnly(principal, periodicRate, term, frequency, firstDueDate);
            default -> levelPayment(principal, periodicRate, term, frequency, firstDueDate, priced);
        };
    }

    public static BigDecimal remaining(BigDecimal opening, BigDecimal priorPrincipal, BigDecimal thisPrincipal) {
        BigDecimal start = opening == null ? BigDecimal.ZERO : opening;
        BigDecimal prior = priorPrincipal == null ? BigDecimal.ZERO : priorPrincipal;
        BigDecimal current = thisPrincipal == null ? BigDecimal.ZERO : thisPrincipal;
        BigDecimal left = start.subtract(prior).subtract(current);
        if (left.signum() < 0) {
            left = BigDecimal.ZERO;
        }
        return money(left);
    }

    public static LocalDate addPeriod(LocalDate date, PaymentFrequencyEnum paymentFrequency) {
        PaymentFrequencyEnum frequency = paymentFrequency == null ? PaymentFrequencyEnum.MONTHLY : paymentFrequency;
        return switch (frequency) {
            case DAILY -> date.plusDays(1);
            case WEEKLY -> date.plusWeeks(1);
            case BIWEEKLY -> date.plusWeeks(2);
            case SEMIMONTHLY -> date.getDayOfMonth() < 15
                    ? date.withDayOfMonth(Math.min(15, date.lengthOfMonth()))
                    : date.plusMonths(1).withDayOfMonth(1);
            case BIMONTHLY -> date.plusMonths(2);
            case QUARTERLY -> date.plusMonths(3);
            case SEMIANNUALLY -> date.plusMonths(6);
            case ANNUALLY -> date.plusYears(1);
            case MONTHLY -> date.plusMonths(1);
        };
    }

    private static int periodsPerYear(PaymentFrequencyEnum paymentFrequency) {
        return switch (paymentFrequency) {
            case DAILY -> 365;
            case WEEKLY -> 52;
            case BIWEEKLY -> 26;
            case SEMIMONTHLY -> 24;
            case BIMONTHLY -> 6;
            case QUARTERLY -> 4;
            case SEMIANNUALLY -> 2;
            case ANNUALLY -> 1;
            case MONTHLY -> 12;
        };
    }

    private static List<Installment> levelPayment(
            BigDecimal principal,
            BigDecimal periodicRate,
            int term,
            PaymentFrequencyEnum frequency,
            LocalDate firstDueDate,
            BigDecimal pricedInstallment) {
        BigDecimal payment = pricedInstallment == null
                ? levelInstallment(principal, periodicRate, term)
                : pricedInstallment;
        List<Installment> rows = new ArrayList<>();
        BigDecimal remaining = principal;
        LocalDate due = firstDueDate;
        for (int number = 1; number <= term; number++) {
            BigDecimal interest = money(remaining.multiply(periodicRate));
            BigDecimal principalPart = number == term
                    ? remaining
                    : payment.subtract(interest).min(remaining).max(BigDecimal.ZERO);
            remaining = remaining.subtract(principalPart);
            rows.add(row(number, due, principalPart, interest, remaining));
            due = addPeriod(due, frequency);
        }
        return rows;
    }

    private static List<Installment> equalPrincipal(
            BigDecimal principal,
            BigDecimal periodicRate,
            int term,
            PaymentFrequencyEnum frequency,
            LocalDate firstDueDate) {
        BigDecimal principalPart = money(principal.divide(BigDecimal.valueOf(term), RATE_SCALE, ROUNDING));
        List<Installment> rows = new ArrayList<>();
        BigDecimal remaining = principal;
        LocalDate due = firstDueDate;
        for (int number = 1; number <= term; number++) {
            BigDecimal thisPrincipal = number == term ? remaining : principalPart.min(remaining);
            BigDecimal interest = money(remaining.multiply(periodicRate));
            remaining = remaining.subtract(thisPrincipal);
            rows.add(row(number, due, thisPrincipal, interest, remaining));
            due = addPeriod(due, frequency);
        }
        return rows;
    }

    private static List<Installment> interestOnly(
            BigDecimal principal,
            BigDecimal periodicRate,
            int term,
            PaymentFrequencyEnum frequency,
            LocalDate firstDueDate) {
        List<Installment> rows = new ArrayList<>();
        LocalDate due = firstDueDate;
        BigDecimal interest = money(principal.multiply(periodicRate));
        for (int number = 1; number <= term; number++) {
            BigDecimal principalPart = number == term ? principal : BigDecimal.ZERO.setScale(MONEY_SCALE, ROUNDING);
            BigDecimal remaining = number == term ? BigDecimal.ZERO : principal;
            rows.add(row(number, due, principalPart, interest, remaining));
            due = addPeriod(due, frequency);
        }
        return rows;
    }

    private static BigDecimal levelInstallment(BigDecimal principal, BigDecimal periodicRate, int term) {
        if (periodicRate.signum() == 0) {
            return money(principal.divide(BigDecimal.valueOf(term), RATE_SCALE, ROUNDING));
        }
        BigDecimal onePlusR = BigDecimal.ONE.add(periodicRate);
        BigDecimal factor = onePlusR.pow(term);
        return money(principal.multiply(periodicRate).multiply(factor)
                .divide(factor.subtract(BigDecimal.ONE), RATE_SCALE, ROUNDING));
    }

    private static Installment row(
            int number, LocalDate due, BigDecimal principalPart, BigDecimal interest, BigDecimal remaining) {
        BigDecimal principalDue = money(principalPart);
        BigDecimal interestDue = money(interest);
        BigDecimal fee = BigDecimal.ZERO.setScale(MONEY_SCALE, ROUNDING);
        return new Installment(number, due, principalDue, interestDue, fee, principalDue.add(interestDue), money(remaining));
    }

    private static BigDecimal money(BigDecimal value) {
        return value.setScale(MONEY_SCALE, ROUNDING);
    }
}
