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


package com.firefly.core.lending.servicing.interfaces.enums;

/**
 * Enumeration of escrow account types for loan servicing.
 *
 * <p>Defines the various types of escrow accounts that can be managed as part of loan servicing
 * across all lending products including mortgages, asset-based lending, trade finance, equipment
 * financing, construction loans, and other loan types.</p>
 *
 * @since 1.0.0
 */
public enum EscrowTypeEnum {

    // ========== Insurance Escrows ==========

    /**
     * General insurance escrow account.
     * Covers any type of insurance premium (property, liability, casualty, etc.).
     */
    INSURANCE,

    /**
     * Collateral insurance escrow account.
     * Insurance on collateral assets (equipment, inventory, vehicles, property, etc.).
     */
    COLLATERAL_INSURANCE,

    /**
     * Credit insurance escrow account.
     * Credit life, disability, or unemployment insurance.
     */
    CREDIT_INSURANCE,

    /**
     * Liability insurance escrow account.
     * General liability, professional liability, or errors & omissions insurance.
     */
    LIABILITY_INSURANCE,

    // ========== Tax Escrows ==========

    /**
     * Tax escrow account.
     * Covers any type of tax payment (property tax, business tax, VAT, etc.).
     */
    TAX,

    /**
     * Property tax escrow account.
     * Real estate or personal property taxes.
     */
    PROPERTY_TAX,

    /**
     * Business tax escrow account.
     * Business license fees, franchise taxes, or other business-related taxes.
     */
    BUSINESS_TAX,

    /**
     * Import/export duty escrow account.
     * Customs duties, tariffs, or trade-related taxes (trade finance).
     */
    IMPORT_EXPORT_DUTY,

    // ========== Maintenance & Operating Escrows ==========

    /**
     * Maintenance reserve escrow account.
     * Scheduled maintenance for equipment, vehicles, or property.
     */
    MAINTENANCE_RESERVE,

    /**
     * Repair escrow account.
     * Funds held for repairs or improvements.
     */
    REPAIR_RESERVE,

    /**
     * Operating expense escrow account.
     * Utilities, common area maintenance, or other operating expenses.
     */
    OPERATING_EXPENSE,

    /**
     * Capital expenditure reserve escrow account.
     * Major capital improvements or replacements.
     */
    CAPEX_RESERVE,

    // ========== Association & Fee Escrows ==========

    /**
     * Association fees escrow account.
     * HOA, condo association, or cooperative fees.
     */
    ASSOCIATION_FEES,

    /**
     * Lease payment escrow account.
     * Ground rent, equipment lease, or other lease obligations.
     */
    LEASE_PAYMENT,

    /**
     * License and permit fees escrow account.
     * Business licenses, permits, or regulatory fees.
     */
    LICENSE_PERMIT_FEES,

    // ========== Trade Finance Escrows ==========

    /**
     * Freight and shipping escrow account.
     * Shipping costs, freight charges, or logistics expenses (trade finance).
     */
    FREIGHT_SHIPPING,

    /**
     * Warehousing escrow account.
     * Storage fees or warehousing costs (trade finance, inventory financing).
     */
    WAREHOUSING,

    /**
     * Letter of credit fees escrow account.
     * LC issuance fees, confirmation fees, or amendment fees.
     */
    LC_FEES,

    // ========== Asset-Based Lending Escrows ==========

    /**
     * Inventory monitoring escrow account.
     * Third-party inventory monitoring or audit fees (asset-based lending).
     */
    INVENTORY_MONITORING,

    /**
     * Appraisal and valuation escrow account.
     * Periodic appraisals or collateral valuations.
     */
    APPRAISAL_VALUATION,

    /**
     * Environmental compliance escrow account.
     * Environmental assessments, remediation, or compliance costs.
     */
    ENVIRONMENTAL_COMPLIANCE,

    // ========== Construction & Development Escrows ==========

    /**
     * Construction holdback escrow account.
     * Funds held back for construction completion or warranty work.
     */
    CONSTRUCTION_HOLDBACK,

    /**
     * Performance bond escrow account.
     * Performance or payment bonds for construction projects.
     */
    PERFORMANCE_BOND,


    /**
     * Retainage escrow account.
     * Contractor retainage or holdback amounts.
     */
    RETAINAGE,

    // ========== Legal & Compliance Escrows ==========

    /**
     * Legal and professional fees escrow account.
     * Attorney fees, accounting fees, or other professional services.
     */
    LEGAL_PROFESSIONAL_FEES,

    /**
     * Regulatory compliance escrow account.
     * Compliance costs, audit fees, or regulatory assessments.
     */
    REGULATORY_COMPLIANCE,

    /**
     * Litigation reserve escrow account.
     * Funds reserved for pending or potential litigation.
     */
    LITIGATION_RESERVE,

    // ========== Debt Service & Payment Escrows ==========

    /**
     * Debt service reserve escrow account.
     * Reserve for debt service payments (common in project finance).
     */
    DEBT_SERVICE_RESERVE,

    /**
     * Interest reserve escrow account.
     * Pre-funded interest payments during construction or development.
     */
    INTEREST_RESERVE,

    /**
     * Principal payment reserve escrow account.
     * Reserve for future principal payments.
     */
    PRINCIPAL_RESERVE,

    // ========== Other Escrows ==========

    /**
     * Security deposit escrow account.
     * Security deposits or good faith deposits.
     */
    SECURITY_DEPOSIT,

    /**
     * Contingency reserve escrow account.
     * General contingency or emergency reserve.
     */
    CONTINGENCY_RESERVE,

    /**
     * Third-party fees escrow account.
     * Fees payable to third parties (servicers, agents, trustees).
     */
    THIRD_PARTY_FEES,

    /**
     * Combined escrow account.
     * Multiple escrow purposes combined into one account.
     */
    COMBINED,

    /**
     * Other escrow types not covered by standard categories.
     */
    OTHER
}

