package com.firefly.core.lending.servicing.models.repositories.servicing.v1;

import com.firefly.core.lending.servicing.models.entities.servicing.v1.LoanServicingEvent;
import com.firefly.core.lending.servicing.models.repositories.BaseRepository;

import java.util.UUID;

public interface LoanServicingEventRepository extends BaseRepository<LoanServicingEvent, UUID> {
}
