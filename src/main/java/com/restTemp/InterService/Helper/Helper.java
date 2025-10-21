package com.restTemp.InterService.Helper;

import com.restTemp.InterService.Dto.AccountDetailsOutputDTO;
import com.restTemp.InterService.Dto.StatementOutputDTO;
import com.restTemp.InterService.Entity.AccountDetails;
import com.restTemp.InterService.Entity.Statement;
import com.restTemp.InterService.Repository.AccountDetailsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Helper {


    private final AccountDetailsRepository accountDetailsRepository;

    public Helper(AccountDetailsRepository accountDetailsRepository) {
        this.accountDetailsRepository = accountDetailsRepository;
    }


    public AccountDetails saveAccountDetails(AccountDetailsOutputDTO dto) {

        // Find existing or create new
        AccountDetails accountDetails = accountDetailsRepository
                .findByBamsId(dto.getBamsId())
                .orElse(new AccountDetails());

        // Map basic fields
        accountDetails.setAccountName(dto.getAccountName());
        accountDetails.setAccountNumber(dto.getAccountNumber());
        accountDetails.setAccountType(dto.getAccountType());
        accountDetails.setAuthorityLimitAggregateMaximum(dto.getAuthorityLimitAggregateMaximum());
        accountDetails.setAuthorityLimitSingleMaximum(dto.getAuthorityLimitSingleMaximum());
        accountDetails.setBamsId(dto.getBamsId());
        accountDetails.setBrokerAgencyName(dto.getBrokerAgencyName());
        accountDetails.setBusinessType(dto.getBusinessType());
        accountDetails.setCity(dto.getCity());
        accountDetails.setCreditLensId(dto.getCreditLensId());
        accountDetails.setEntityType(dto.getEntityType());
        accountDetails.setNaicsNumber(dto.getNaicsNumber());
        accountDetails.setPeOwned(dto.getPeOwned());
        accountDetails.setProducerName(dto.getProducerName());
        accountDetails.setState(dto.getState());
        accountDetails.setStreetAddress1(dto.getStreetAddress1());
        accountDetails.setStreetAddress2(dto.getStreetAddress2());
        accountDetails.setTenureOfTheAccount(dto.getTenureOfTheAccount());
        accountDetails.setTickerSymbol(dto.getTickerSymbol());
        accountDetails.setUnderwriterName(dto.getUnderwriterName());
        accountDetails.setZip(dto.getZip());

        // Save statements
        if (dto.getStatementOutputs() != null && !dto.getStatementOutputs().isEmpty()) {
            List<Statement> statements = new ArrayList<>();

            for (StatementOutputDTO stmtDto : dto.getStatementOutputs()) {
                Statement statement = new Statement();
                statement.setCash(parseBigDecimal(stmtDto.getCash()));
                statement.setCashColor(stmtDto.getCashColor());
                statement.setNetWorth(parseBigDecimal(stmtDto.getNetWorth()));
                statement.setNetWorthColor(stmtDto.getNetWorthColor());
                statement.setRevenueSize(parseBigDecimal(stmtDto.getRevenueSize()));
                statement.setStatementDate(parseDateTime(stmtDto.getStatementDate()));
                statement.setAccountDetails(accountDetails);
                statements.add(statement);
            }

            accountDetails.setStatements(statements);
        }

        accountDetails = accountDetailsRepository.save(accountDetails);


        return accountDetails;
    }



    public BigDecimal parseBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            // Remove commas and parse
            String cleanValue = value.replace(",", "").trim();
            return new BigDecimal(cleanValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public LocalDateTime parseDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return null;
        }

        try {
            // Format: "2024-01-31 00:00:00.000 -0800"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS Z");
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            try {
                // Format: "2024-01-31 00:00:00"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return LocalDateTime.parse(dateTimeString, formatter);
            } catch (DateTimeParseException e2) {

                return null;
            }
        }
    }


}
