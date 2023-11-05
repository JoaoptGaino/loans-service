package com.br.joaoptgaino.loans.dto.loans;


import com.br.joaoptgaino.loans.model.enums.LoanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanDTO {
    private LoanType type;
    private Integer interestRate;
}
