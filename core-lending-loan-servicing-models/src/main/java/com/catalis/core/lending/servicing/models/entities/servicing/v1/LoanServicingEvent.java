package com.catalis.core.lending.servicing.models.entities.servicing.v1;

import com.catalis.core.lending.servicing.interfaces.enums.servicing.v1.EventTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("loan_servicing_event")
public class LoanServicingEvent {

    @Id
    @Column("loan_servicing_event_id")
    private Long loanServicingEventId;

    @Column("loan_servicing_case_id")
    private Long loanServicingCaseId;

    @Column("event_type")
    private EventTypeEnum eventType;

    @Column("event_date")
    private LocalDate eventDate;

    @Column("description")
    private String description;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}