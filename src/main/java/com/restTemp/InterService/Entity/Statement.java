package com.restTemp.InterService.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "statements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cash", precision = 19, scale = 2)
    private BigDecimal cash;

    @Column(name = "cash_color")
    private String cashColor;

    @Column(name = "net_worth", precision = 19, scale = 2)
    private BigDecimal netWorth;

    @Column(name = "net_worth_color")
    private String netWorthColor;

    @Column(name = "revenue_size", precision = 19, scale = 2)
    private BigDecimal revenueSize;

    @Column(name = "statement_date")
    private LocalDateTime statementDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_details_id", nullable = false)
    private AccountDetails accountDetails;
}
