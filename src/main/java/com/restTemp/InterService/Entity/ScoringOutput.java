package com.restTemp.InterService.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "scoring_outputs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoringOutput {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "future_net_cash_flow")
    private String futureNetCashFlow;

    @Column(name = "risk_scale")
    private String riskScale;

    @Column(name = "risk_scale_color")
    private String riskScaleColor;

    @Column(name = "total_score")
    private String totalScore;

    @Column(name = "total_score_color")
    private String totalScoreColor;

    @Column(name = "total_work_program")
    private String totalWorkProgram;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scoring_statement_id", nullable = false)
    private ScoringStatement scoringStatement;

    @OneToMany(mappedBy = "scoringOutput", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CategoryOutput> categoryOutputs;
}
