package com.br.joaoptgaino.loans.fixtures;

import com.br.joaoptgaino.loans.dto.loans.LoanDTO;
import com.br.joaoptgaino.loans.model.Loan;
import com.br.joaoptgaino.loans.model.enums.LoanType;

import java.util.UUID;

public class LoanFixture {
    public static final UUID LOAN_ID = UUID.fromString("88a59852-f009-4545-8f1a-dc7eca29ddc1");

    public static Loan getLoan(LoanType type) {
        return Loan.builder()
                .id(LOAN_ID)
                .type(type)
                .build();
    }

    public static LoanDTO getLoanDTO(LoanType type) {
        return LoanDTO.builder()
                .type(type)
                .build();
    }
}
