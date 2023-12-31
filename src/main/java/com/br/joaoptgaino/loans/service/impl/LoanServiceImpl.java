package com.br.joaoptgaino.loans.service.impl;

import com.br.joaoptgaino.loans.dto.loans.LoanDTO;
import com.br.joaoptgaino.loans.mappers.LoanMapper;
import com.br.joaoptgaino.loans.repository.LoanRepository;
import com.br.joaoptgaino.loans.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record LoanServiceImpl(LoanRepository loanRepository) implements LoanService {
    @Override
    public List<LoanDTO> findAll() {
        return loanRepository.findAll().stream()
                .map(LoanMapper.INSTANCE::loanToLoanDTO)
                .toList();
    }
}
