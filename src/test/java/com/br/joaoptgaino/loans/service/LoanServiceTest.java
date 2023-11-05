package com.br.joaoptgaino.loans.service;

import com.br.joaoptgaino.loans.dto.loans.LoanDTO;
import com.br.joaoptgaino.loans.mappers.CustomerMapper;
import com.br.joaoptgaino.loans.mappers.LoanMapper;
import com.br.joaoptgaino.loans.model.Loan;
import com.br.joaoptgaino.loans.model.enums.LoanType;
import com.br.joaoptgaino.loans.repository.CustomerRepository;
import com.br.joaoptgaino.loans.repository.LoanRepository;
import com.br.joaoptgaino.loans.service.impl.CustomerServiceImpl;
import com.br.joaoptgaino.loans.service.impl.LoanServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.br.joaoptgaino.loans.fixtures.LoanFixture.getLoan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class LoanServiceTest {

    @InjectMocks
    private LoanServiceImpl loanService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanMapper loanMapper;

    @Test
    public void shouldFindAllLoansWithSuccess() {
        List<Loan> loans = List.of(
                getLoan(LoanType.PERSONAL),
                getLoan(LoanType.CONSIGNMENT),
                getLoan(LoanType.GUARANTEED));
        when(loanRepository.findAll()).thenReturn(loans);
        List<LoanDTO> loanDTOs = loanService.findAll();
        assertEquals(loanDTOs.size(), 3);
        verify(loanRepository, times(1)).findAll();

    }
}
