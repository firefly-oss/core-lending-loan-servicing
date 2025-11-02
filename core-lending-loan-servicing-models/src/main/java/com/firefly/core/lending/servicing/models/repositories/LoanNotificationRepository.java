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


package com.firefly.core.lending.servicing.models.repositories;

import com.firefly.core.lending.servicing.interfaces.enums.NotificationStatusEnum;
import com.firefly.core.lending.servicing.models.entities.LoanNotification;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface LoanNotificationRepository extends BaseRepository<LoanNotification, UUID> {
    
    Flux<LoanNotification> findByLoanServicingCaseIdOrderByCreatedAtDesc(UUID loanServicingCaseId);
    
    Flux<LoanNotification> findByRecipientPartyIdOrderByCreatedAtDesc(UUID recipientPartyId);
    
    Flux<LoanNotification> findByNotificationStatusOrderByScheduledSendTimeAsc(NotificationStatusEnum status);
    
    Flux<LoanNotification> findByLoanServicingCaseIdAndRecipientPartyIdOrderByCreatedAtDesc(UUID loanServicingCaseId, UUID recipientPartyId);
}

