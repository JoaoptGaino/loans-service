package com.br.joaoptgaino.loans.dto.customer;

import com.br.joaoptgaino.loans.dto.loans.LoanDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLoanDTO {
    private String customer;
    private List<LoanDTO> loans = new ArrayList<>();
}
