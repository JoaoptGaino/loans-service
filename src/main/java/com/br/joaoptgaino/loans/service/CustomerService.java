package com.br.joaoptgaino.loans.service;

import com.br.joaoptgaino.loans.common.CrudCommonService;
import com.br.joaoptgaino.loans.dto.customer.CustomerDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerFormDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerLoanDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerParamsDTO;

import java.util.UUID;

public interface CustomerService extends CrudCommonService<CustomerDTO, CustomerFormDTO, CustomerParamsDTO> {
    CustomerLoanDTO findLoansForUser(UUID id);
}
