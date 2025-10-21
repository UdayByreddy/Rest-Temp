package com.restTemp.InterService.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "scoring_statements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoringStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_status")
    private String accountStatus;

    @Column(name = "prepared_by")
    private String preparedBy;

    @Column(name = "statement_date")
    private LocalDateTime statementDate;

    @Column(name = "stmt_type")
    private String stmtType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bams_scoring_id", nullable = false)
    private BamsScoring bamsScoring;

    @OneToOne(mappedBy = "scoringStatement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private FinancialMetric financialMetric;

    @OneToOne(mappedBy = "scoringStatement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ScoringOutput scoringOutput;
}
