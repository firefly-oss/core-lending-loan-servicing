package com.firefly.core.lending.servicing.models.repositories.rate.v1;

import com.firefly.core.lending.servicing.models.entities.rate.v1.LoanRateChange;
import com.firefly.core.lending.servicing.models.repositories.BaseRepository;

import java.util.UUID;

public interface LoanRateChangeRepository extends BaseRepository<LoanRateChange, UUID> {
}
