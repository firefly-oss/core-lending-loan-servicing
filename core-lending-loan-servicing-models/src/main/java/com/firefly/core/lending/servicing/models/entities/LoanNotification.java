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


package com.firefly.core.lending.servicing.models.entities;

import com.firefly.core.lending.servicing.interfaces.enums.NotificationChannelEnum;
import com.firefly.core.lending.servicing.interfaces.enums.NotificationStatusEnum;
import com.firefly.core.lending.servicing.interfaces.enums.NotificationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing notifications sent to loan parties.
 * Parties are derived from the application and contract associated with the loan.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("loan_notification")
public class LoanNotification {

    @Id
    @Column("loan_notification_id")
    private UUID loanNotificationId;

    @Column("loan_servicing_case_id")
    private UUID loanServicingCaseId;

    @Column("notification_type")
    private NotificationTypeEnum notificationType;

    @Column("notification_channel")
    private NotificationChannelEnum notificationChannel;

    @Column("notification_status")
    private NotificationStatusEnum notificationStatus;

    @Column("recipient_party_id")
    private UUID recipientPartyId; // Party ID from application or contract

    @Column("recipient_name")
    private String recipientName;

    @Column("recipient_contact")
    private String recipientContact; // Email, phone number, or address depending on channel

    @Column("subject")
    private String subject;

    @Column("message_body")
    private String messageBody;

    @Column("scheduled_send_time")
    private LocalDateTime scheduledSendTime;

    @Column("sent_time")
    private LocalDateTime sentTime;

    @Column("delivered_time")
    private LocalDateTime deliveredTime;

    @Column("read_time")
    private LocalDateTime readTime;

    @Column("failure_reason")
    private String failureReason; // Reason if delivery failed

    @Column("retry_count")
    private Integer retryCount;

    @Column("template_id")
    private String templateId; // Reference to notification template used

    @Column("metadata")
    private String metadata; // JSON metadata for additional context

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}

