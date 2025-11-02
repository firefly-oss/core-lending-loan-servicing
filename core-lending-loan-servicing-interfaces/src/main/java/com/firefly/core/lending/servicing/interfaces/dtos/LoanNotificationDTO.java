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


package com.firefly.core.lending.servicing.interfaces.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firefly.core.lending.servicing.interfaces.enums.NotificationChannelEnum;
import com.firefly.core.lending.servicing.interfaces.enums.NotificationStatusEnum;
import com.firefly.core.lending.servicing.interfaces.enums.NotificationTypeEnum;
import com.firefly.core.utils.annotations.FilterableId;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanNotificationDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID loanNotificationId;

    @FilterableId
    @NotNull(message = "Loan servicing case ID is required")
    private UUID loanServicingCaseId;

    @NotNull(message = "Notification type is required")
    private NotificationTypeEnum notificationType;

    @NotNull(message = "Notification channel is required")
    private NotificationChannelEnum notificationChannel;

    @NotNull(message = "Notification status is required")
    private NotificationStatusEnum notificationStatus;

    @FilterableId
    @NotNull(message = "Recipient party ID is required")
    private UUID recipientPartyId;

    @NotBlank(message = "Recipient name is required")
    @Size(max = 255, message = "Recipient name cannot exceed 255 characters")
    private String recipientName;

    @NotBlank(message = "Recipient contact is required")
    @Size(max = 255, message = "Recipient contact cannot exceed 255 characters")
    private String recipientContact;

    @NotBlank(message = "Subject is required")
    @Size(max = 500, message = "Subject cannot exceed 500 characters")
    private String subject;

    @NotBlank(message = "Message body is required")
    @Size(max = 5000, message = "Message body cannot exceed 5000 characters")
    private String messageBody;

    @Future(message = "Scheduled send time must be in the future")
    private LocalDateTime scheduledSendTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime sentTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime deliveredTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime readTime;

    @Size(max = 500, message = "Failure reason cannot exceed 500 characters")
    private String failureReason;

    @Min(value = 0, message = "Retry count cannot be negative")
    private Integer retryCount;

    @Size(max = 100, message = "Template ID cannot exceed 100 characters")
    private String templateId;

    @Size(max = 2000, message = "Metadata cannot exceed 2000 characters")
    private String metadata;
}

