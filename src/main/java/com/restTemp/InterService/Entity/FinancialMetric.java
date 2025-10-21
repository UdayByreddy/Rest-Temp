package com.restTemp.InterService.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "financial_metrics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "debt_to_net_worth", precision = 19, scale = 2)
    private BigDecimal debtToNetWorth;

    @Column(name = "debt_to_net_worth_color")
    private String debtToNetWorthColor;

    @Column(name = "free_cash_flow", precision = 19, scale = 2)
    private BigDecimal freeCashFlow;

    @Column(name = "free_cash_flow_color")
    private String freeCashFlowColor;

    @Column(name = "ibd_to_net_worth", precision = 19, scale = 2)
    private BigDecimal ibdToNetWorth;

    @Column(name = "ibd_to_net_worth_color")
    private String ibdToNetWorthColor;

    @Column(name = "interest_coverage", precision = 19, scale = 2)
    private BigDecimal interestCoverage;

    @Column(name = "interest_coverage_color")
    private String interestCoverageColor;

    @Column(name = "net_profit_margin", precision = 19, scale = 4)
    private BigDecimal netProfitMargin;

    @Column(name = "net_profit_margin_color")
    private String netProfitMarginColor;

    @Column(name = "operating_margin", precision = 19, scale = 4)
    private BigDecimal operatingMargin;

    @Column(name = "operating_margin_color")
    private String operatingMarginColor;

    @Column(name = "profitability_ratio", precision = 19, scale = 2)
    private BigDecimal profitabilityRatio;

    @Column(name = "profitability_ratio_color")
    private String profitabilityRatioColor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scoring_statement_id", nullable = false)
    private ScoringStatement scoringStatement;
}
