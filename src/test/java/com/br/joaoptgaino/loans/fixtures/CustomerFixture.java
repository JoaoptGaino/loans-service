package com.br.joaoptgaino.loans.fixtures;

import com.br.joaoptgaino.loans.dto.customer.CustomerDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerFormDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerLoanDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerParamsDTO;
import com.br.joaoptgaino.loans.model.Customer;
import com.br.joaoptgaino.loans.model.enums.LoanType;

import java.util.List;
import java.util.UUID;

import static com.br.joaoptgaino.loans.fixtures.LoanFixture.getLoanDTO;

public class CustomerFixture {
    public static final UUID CUSTOMER_ID = UUID.fromString("88a59852-f009-4545-8f1a-dc7eca29ddc1");

    public static Customer getCustomer(Boolean withId, String name, String cpf, String location, Float income, Integer age) {
        return Customer.builder()
                .id(withId ? CUSTOMER_ID : null)
                .name(name)
                .cpf(cpf)
                .location(location)
                .income(income)
                .age(age)
                .build();
    }

    public static CustomerDTO getCustomerDTO(String name, String cpf, String location, Float income, Integer age) {
        return CustomerDTO.builder()
                .id(CUSTOMER_ID)
                .name(name)
                .cpf(cpf)
                .location(location)
                .income(income)
                .age(age)
                .build();
    }

    public static CustomerFormDTO getCustomerFormDTO(String name, String cpf, String location, Float income, Integer age) {
        return CustomerFormDTO.builder()
                .name(name)
                .cpf(cpf)
                .location(location)
                .income(income)
                .age(age)
                .build();
    }

    public static CustomerParamsDTO getCustomerParamsDTO(String name, String cpf, String location, Float income, Integer age) {
        return CustomerParamsDTO.builder()
                .name(name)
                .cpf(cpf)
                .location(location)
                .income(income)
                .age(age)
                .build();
    }

    public static CustomerLoanDTO getCustomerLoanDTO(String customer) {
        return CustomerLoanDTO.builder()
                .customer(customer)
                .loans(List.of(getLoanDTO(LoanType.PERSONAL)))
                .build();
    }
}
