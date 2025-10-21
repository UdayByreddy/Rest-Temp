package com.restTemp.InterService.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "category_outputs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryOutput {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_score")
    private String categoryScore;

    @Column(name = "category_score_color")
    private String categoryScoreColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scoring_output_id", nullable = false)
    private ScoringOutput scoringOutput;

    @OneToMany(mappedBy = "categoryOutput", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<VariableOutput> variableOutputs;
}
