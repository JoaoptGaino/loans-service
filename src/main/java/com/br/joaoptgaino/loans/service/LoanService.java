package com.br.joaoptgaino.loans.service;

import com.br.joaoptgaino.loans.dto.loans.LoanDTO;

import java.util.List;

public interface LoanService {
    List<LoanDTO> findAll();
}
