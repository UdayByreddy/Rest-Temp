package com.restTemp.InterService.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import java.math.BigDecimal;
//import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "account_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "authority_limit_aggregate_maximum")
    private String authorityLimitAggregateMaximum;

    @Column(name = "authority_limit_single_maximum")
    private String authorityLimitSingleMaximum;

    @Column(name = "bams_id", unique = true)
    private Integer bamsId;

    @Column(name = "broker_agency_name")
    private String brokerAgencyName;

    @Column(name = "business_type")
    private String businessType;

    @Column(name = "city")
    private String city;

    @Column(name = "credit_lens_id")
    private Integer creditLensId;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "naics_number")
    private String naicsNumber;

    @Column(name = "pe_owned")
    private String peOwned;

    @Column(name = "producer_name")
    private String producerName;

    @Column(name = "state")
    private String state;

    @Column(name = "street_address1")
    private String streetAddress1;

    @Column(name = "street_address2")
    private String streetAddress2;

    @Column(name = "tenure_of_the_account")
    private String tenureOfTheAccount;

    @Column(name = "ticker_symbol")
    private String tickerSymbol;

    @Column(name = "underwriter_name")
    private String underwriterName;

    @Column(name = "zip")
    private String zip;

    @OneToMany(mappedBy = "accountDetails", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Statement> statements;
}
