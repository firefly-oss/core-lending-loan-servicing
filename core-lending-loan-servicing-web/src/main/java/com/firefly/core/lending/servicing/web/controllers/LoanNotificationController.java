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


package com.firefly.core.lending.servicing.web.controllers;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.lending.servicing.core.services.LoanNotificationService;
import com.firefly.core.lending.servicing.interfaces.dtos.LoanNotificationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loan-servicing-cases/{caseId}/notifications")
@Tag(name = "Loan Notification", description = "Manage loan notifications sent to parties via email, SMS, push, in-app, mail, or phone")
@RequiredArgsConstructor
public class LoanNotificationController {

    private final LoanNotificationService service;

    @GetMapping
    @Operation(
            summary = "List notifications",
            description = "Retrieve all notifications for a specific loan servicing case with pagination and filtering support"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved notifications",
                    content = @Content(schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content),
            @ApiResponse(responseCode = "404", description = "Loan servicing case not found", content = @Content)
    })
    public Mono<ResponseEntity<PaginationResponse<LoanNotificationDTO>>> findAllNotifications(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Filter and pagination parameters")
            @ModelAttribute FilterRequest<LoanNotificationDTO> filterRequest) {

        return service.findAll(loanServicingCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
            summary = "Create notification",
            description = "Create a new notification for a loan servicing case"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification created successfully",
                    content = @Content(schema = @Schema(implementation = LoanNotificationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Loan servicing case not found", content = @Content)
    })
    public Mono<ResponseEntity<LoanNotificationDTO>> createNotification(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Notification data", required = true)
            @Valid @RequestBody LoanNotificationDTO dto) {

        return service.create(loanServicingCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{notificationId}")
    @Operation(
            summary = "Get notification by ID",
            description = "Retrieve a specific notification by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification found",
                    content = @Content(schema = @Schema(implementation = LoanNotificationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Notification not found", content = @Content)
    })
    public Mono<ResponseEntity<LoanNotificationDTO>> getNotificationById(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Notification ID", required = true)
            @PathVariable("notificationId") UUID loanNotificationId) {

        return service.getById(loanServicingCaseId, loanNotificationId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{notificationId}")
    @Operation(
            summary = "Update notification",
            description = "Update an existing notification (read-only timestamp fields cannot be updated)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification updated successfully",
                    content = @Content(schema = @Schema(implementation = LoanNotificationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Notification not found", content = @Content)
    })
    public Mono<ResponseEntity<LoanNotificationDTO>> updateNotification(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Notification ID", required = true)
            @PathVariable("notificationId") UUID loanNotificationId,
            @Parameter(description = "Updated notification data", required = true)
            @Valid @RequestBody LoanNotificationDTO dto) {

        return service.update(loanServicingCaseId, loanNotificationId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{notificationId}")
    @Operation(
            summary = "Delete notification",
            description = "Delete a notification"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notification deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Notification not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteNotification(
            @Parameter(description = "Loan servicing case ID", required = true)
            @PathVariable("caseId") UUID loanServicingCaseId,
            @Parameter(description = "Notification ID", required = true)
            @PathVariable("notificationId") UUID loanNotificationId) {

        return service.delete(loanServicingCaseId, loanNotificationId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}

