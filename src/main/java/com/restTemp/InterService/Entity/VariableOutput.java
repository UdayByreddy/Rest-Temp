package com.restTemp.InterService.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "variable_outputs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariableOutput {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ========== Liquidity Variables ==========
    @Column(name = "accounts_receivable_turnover_ratio", precision = 19, scale = 2)
    private BigDecimal accountsReceivableTurnoverRatio;

    @Column(name = "accounts_receivable_turnover_score", precision = 19, scale = 2)
    private BigDecimal accountsReceivableTurnoverScore;

    @Column(name = "current_assets_by_current_liabilities_ratio", precision = 19, scale = 2)
    private BigDecimal currentAssetsByCurrentLiabilitiesRatio;

    @Column(name = "current_assets_by_current_liabilities_score", precision = 19, scale = 2)
    private BigDecimal currentAssetsByCurrentLiabilitiesScore;

    @Column(name = "net_cash_by_total_assets_ratio", precision = 19, scale = 2)
    private BigDecimal netCashByTotalAssetsRatio;

    @Column(name = "net_cash_by_total_assets_score", precision = 19, scale = 2)
    private BigDecimal netCashByTotalAssetsScore;

    @Column(name = "underbillings_by_working_capital_ratio", precision = 19, scale = 2)
    private BigDecimal underbillingsByWorkingCapitalRatio;

    @Column(name = "underbillings_by_working_capital_score", precision = 19, scale = 2)
    private BigDecimal underbillingsByWorkingCapitalScore;

    // ========== Leverage Variables ==========
    @Column(name = "current_bank_debt_by_total_assets_ratio", precision = 19, scale = 2)
    private BigDecimal currentBankDebtByTotalAssetsRatio;

    @Column(name = "current_bank_debt_by_total_assets_score", precision = 19, scale = 2)
    private BigDecimal currentBankDebtByTotalAssetsScore;

    @Column(name = "total_bank_debt_by_total_assets_ratio", precision = 19, scale = 2)
    private BigDecimal totalBankDebtByTotalAssetsRatio;

    @Column(name = "total_bank_debt_by_total_assets_score", precision = 19, scale = 2)
    private BigDecimal totalBankDebtByTotalAssetsScore;

    @Column(name = "total_liabilities_by_equity_ratio", precision = 19, scale = 2)
    private BigDecimal totalLiabilitiesByEquityRatio;

    @Column(name = "total_liabilities_by_equity_score", precision = 19, scale = 2)
    private BigDecimal totalLiabilitiesByEquityScore;

    @Column(name = "working_capital_by_current_assets_ratio", precision = 19, scale = 2)
    private BigDecimal workingCapitalByCurrentAssetsRatio;

    @Column(name = "working_capital_by_current_assets_score", precision = 19, scale = 2)
    private BigDecimal workingCapitalByCurrentAssetsScore;

    // ========== Operational Variables ==========
    @Column(name = "gross_profit_margin_ratio", precision = 19, scale = 2)
    private BigDecimal grossProfitMarginRatio;

    @Column(name = "gross_profit_margin_score", precision = 19, scale = 2)
    private BigDecimal grossProfitMarginScore;

    @Column(name = "net_profit_margin_ratio", precision = 19, scale = 2)
    private BigDecimal netProfitMarginRatio;

    @Column(name = "net_profit_margin_score", precision = 19, scale = 2)
    private BigDecimal netProfitMarginScore;

    @Column(name = "net_worth_by_work_program_ratio", precision = 19, scale = 2)
    private BigDecimal netWorthByWorkProgramRatio;

    @Column(name = "net_worth_by_work_program_score", precision = 19, scale = 2)
    private BigDecimal netWorthByWorkProgramScore;

    @Column(name = "overhead_by_operating_income_ratio", precision = 19, scale = 2)
    private BigDecimal overheadByOperatingIncomeRatio;

    @Column(name = "overhead_by_operating_income_score", precision = 19, scale = 2)
    private BigDecimal overheadByOperatingIncomeScore;

    @Column(name = "working_capital_by_work_program_ratio", precision = 19, scale = 2)
    private BigDecimal workingCapitalByWorkProgramRatio;

    @Column(name = "working_capital_by_work_program_score", precision = 19, scale = 2)
    private BigDecimal workingCapitalByWorkProgramScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_output_id", nullable = false)
    private CategoryOutput categoryOutput;
}
