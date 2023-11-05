package com.br.joaoptgaino.loans.service.impl;

import com.br.joaoptgaino.loans.dto.customer.CustomerDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerFormDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerLoanDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerParamsDTO;
import com.br.joaoptgaino.loans.dto.loans.LoanDTO;
import com.br.joaoptgaino.loans.exception.BusinessException;
import com.br.joaoptgaino.loans.mappers.CustomerMapper;
import com.br.joaoptgaino.loans.mappers.LoanMapper;
import com.br.joaoptgaino.loans.model.Customer;
import com.br.joaoptgaino.loans.model.Loan;
import com.br.joaoptgaino.loans.model.enums.LoanType;
import com.br.joaoptgaino.loans.repository.CustomerRepository;
import com.br.joaoptgaino.loans.repository.LoanRepository;
import com.br.joaoptgaino.loans.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final LoanRepository loanRepository;

    @Override
    public Page<CustomerDTO> findAll(Pageable pageable, CustomerParamsDTO paramsDTO) {
        List<CustomerDTO> customers = customerRepository.findAll(pageable)
                .map(CustomerMapper.INSTANCE::customerToCustomerDTO)
                .stream()
                .toList();

        return new PageImpl<>(customers, pageable, customers.size());
    }

    @Override
    public CustomerDTO findOne(UUID id) {
        Customer customer = getCustomerOrElseThrow(id);
        return CustomerMapper.INSTANCE.customerToCustomerDTO(customer);
    }

    @Override
    public CustomerDTO create(CustomerFormDTO formDTO) {
        Customer customer = CustomerMapper.INSTANCE.customerFormDTOToCustomer(formDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerMapper.INSTANCE.customerToCustomerDTO(savedCustomer);
    }

    @Override
    public CustomerDTO update(UUID id, CustomerFormDTO formDTO) {
        Customer customerInDb = getCustomerOrElseThrow(id);
        Customer customer = updateCustomer(customerInDb, formDTO);
        customerRepository.save(customer);
        return CustomerMapper.INSTANCE.customerToCustomerDTO(customer);
    }

    @Override
    public void delete(UUID id) {
        Customer customer = getCustomerOrElseThrow(id);
        customerRepository.delete(customer);
    }

    @Override
    public CustomerLoanDTO findLoansForUser(UUID id) {
        Customer customer = getCustomerOrElseThrow(id);
        List<LoanDTO> userLoans = verifyLoansForUser(customer);
        return CustomerMapper.INSTANCE.customerToCustomerLoanDTO(customer, userLoans);
    }

    private List<LoanDTO> verifyLoansForUser(Customer customer) {
        List<LoanDTO> userLoans = new ArrayList<>();
        if (customer.getIncome() <= 3000) {
            List<LoanDTO> loans = List.of(LoanMapper.INSTANCE.loanToLoanDTO(loanRepository.findByLoanType(LoanType.PERSONAL.toString())),
                    LoanMapper.INSTANCE.loanToLoanDTO(loanRepository.findByLoanType(LoanType.GUARANTEED.toString())));
            userLoans.addAll(loans);
        } else if (customer.getIncome() > 3000 && customer.getIncome() <= 5000 && customer.getAge() < 30 && customer.getLocation().equals("SP")) {
            List<LoanDTO> loans = List.of(LoanMapper.INSTANCE.loanToLoanDTO(loanRepository.findByLoanType(LoanType.PERSONAL.toString())),
                    LoanMapper.INSTANCE.loanToLoanDTO(loanRepository.findByLoanType(LoanType.GUARANTEED.toString())));
            userLoans.addAll(loans);
        } else if (customer.getIncome() >= 5000) {
            Loan loan = loanRepository.findByLoanType("PERSONAL");
            userLoans.add(LoanMapper.INSTANCE.loanToLoanDTO(loan));
        }
        return userLoans;
    }

    private Customer getCustomerOrElseThrow(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> BusinessException.builder().
                        httpStatus(HttpStatus.NOT_FOUND.value())
                        .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .errors(Collections.singletonList(String.format("Customer with id %s not found", id)))
                        .build());
    }

    private Customer updateCustomer(Customer customer, CustomerFormDTO customerFormDTO) {
        return Customer.builder()
                .id(customer.getId())
                .name(customerFormDTO.getName())
                .cpf(customerFormDTO.getCpf())
                .age(customerFormDTO.getAge())
                .income(customerFormDTO.getIncome())
                .location(customerFormDTO.getLocation())
                .build();
    }
}
