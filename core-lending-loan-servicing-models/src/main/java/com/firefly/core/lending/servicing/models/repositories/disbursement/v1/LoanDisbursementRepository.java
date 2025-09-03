package com.firefly.core.lending.servicing.models.repositories.disbursement.v1;

import com.firefly.core.lending.servicing.models.entities.disbursement.v1.LoanDisbursement;
import com.firefly.core.lending.servicing.models.repositories.BaseRepository;

import java.util.UUID;

public interface LoanDisbursementRepository extends BaseRepository<LoanDisbursement, UUID> {
}
