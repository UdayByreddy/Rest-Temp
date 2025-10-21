package com.restTemp.InterService.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "bams_scoring")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BamsScoring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "bams_id", unique = true)
    private String bamsId;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "business_type")
    private String businessType;

    @Column(name = "score_status")
    private String scoreStatus;

    @OneToMany(mappedBy = "bamsScoring", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ScoringStatement> scoringStatements;
}

