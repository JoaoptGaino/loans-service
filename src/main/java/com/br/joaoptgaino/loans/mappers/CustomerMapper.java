package com.br.joaoptgaino.loans.mappers;

import com.br.joaoptgaino.loans.dto.customer.CustomerDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerFormDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerLoanDTO;
import com.br.joaoptgaino.loans.dto.loans.LoanDTO;
import com.br.joaoptgaino.loans.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerDTOToCustomer(CustomerDTO customerDTO);

    Customer customerFormDTOToCustomer(CustomerFormDTO customerFormDTO);

    @Mapping(source = "customer.name", target = "customer")
    CustomerLoanDTO customerToCustomerLoanDTO(Customer customer, List<LoanDTO> loans);
}
