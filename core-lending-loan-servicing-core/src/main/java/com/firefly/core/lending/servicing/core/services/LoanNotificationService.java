/*
 * Copyright 2025 Firefly Software Solutions Inc
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


package com.firefly.core.lending.servicing.core.services;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanNotificationDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Service interface for managing loan notifications.
 *
 * <p>This service handles the creation, retrieval, update, and deletion of notifications
 * sent to loan parties (borrowers, co-borrowers, guarantors, etc.). Notifications are derived
 * from parties in the application and contract.</p>
 *
 * <p>The service supports multiple notification types covering the entire loan lifecycle:</p>
 * <ul>
 *   <li><strong>Payment notifications:</strong> due reminders, received confirmations, overdue notices, failures</li>
 *   <li><strong>Disbursement notifications:</strong> scheduled, completed, failed</li>
 *   <li><strong>Rate and terms:</strong> rate changes, restructuring, modifications</li>
 *   <li><strong>Maturity and payoff:</strong> approaching maturity, paid off, payoff quotes</li>
 *   <li><strong>Delinquency:</strong> grace period, delinquency, default, collections, foreclosure</li>
 *   <li><strong>Statements:</strong> monthly, annual, tax statements</li>
 *   <li><strong>Escrow:</strong> payments, analysis, shortage/surplus</li>
 *   <li><strong>Rebates:</strong> processed, approved</li>
 *   <li><strong>Account events:</strong> activation, suspension, closure, transfers</li>
 * </ul>
 *
 * <p>Notifications can be delivered through multiple channels (email, SMS, push, in-app, mail, phone)
 * and include delivery tracking (sent, delivered, read) and retry logic.</p>
 *
 * <p>All operations are scoped to a specific loan servicing case to ensure proper isolation
 * and security.</p>
 *
 * @since 1.0.0
 */
public interface LoanNotificationService {

    /**
     * Retrieves all notifications for a specific loan servicing case with pagination and filtering.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param filterRequest the filter and pagination request
     * @return a Mono emitting a paginated response of notification DTOs
     */
    Mono<PaginationResponse<LoanNotificationDTO>> findAll(UUID loanServicingCaseId, FilterRequest<LoanNotificationDTO> filterRequest);

    /**
     * Creates a new notification for a loan servicing case.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param dto the notification data to create
     * @return a Mono emitting the created notification DTO
     */
    Mono<LoanNotificationDTO> create(UUID loanServicingCaseId, LoanNotificationDTO dto);

    /**
     * Retrieves a specific notification by its ID.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param loanNotificationId the UUID of the notification
     * @return a Mono emitting the notification DTO, or empty if not found
     */
    Mono<LoanNotificationDTO> getById(UUID loanServicingCaseId, UUID loanNotificationId);

    /**
     * Updates an existing notification.
     *
     * <p>Note: Read-only timestamp fields (sentTime, deliveredTime, readTime) cannot be updated
     * through this method and will be ignored if present in the DTO.</p>
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param loanNotificationId the UUID of the notification to update
     * @param dto the updated notification data
     * @return a Mono emitting the updated notification DTO
     */
    Mono<LoanNotificationDTO> update(UUID loanServicingCaseId, UUID loanNotificationId, LoanNotificationDTO dto);

    /**
     * Deletes a notification.
     *
     * @param loanServicingCaseId the UUID of the loan servicing case
     * @param loanNotificationId the UUID of the notification to delete
     * @return a Mono that completes when the deletion is successful
     */
    Mono<Void> delete(UUID loanServicingCaseId, UUID loanNotificationId);
}

