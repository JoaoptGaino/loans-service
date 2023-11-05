package com.br.joaoptgaino.loans.service;

import com.br.joaoptgaino.loans.dto.customer.CustomerDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerFormDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerLoanDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerParamsDTO;
import com.br.joaoptgaino.loans.exception.BusinessException;
import com.br.joaoptgaino.loans.fixtures.LoanFixture;
import com.br.joaoptgaino.loans.mappers.CustomerMapper;
import com.br.joaoptgaino.loans.model.Customer;
import com.br.joaoptgaino.loans.model.enums.LoanType;
import com.br.joaoptgaino.loans.repository.CustomerRepository;
import com.br.joaoptgaino.loans.repository.LoanRepository;
import com.br.joaoptgaino.loans.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.br.joaoptgaino.loans.fixtures.CustomerFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private CustomerMapper customerMapper;


    @Test
    public void shouldCreateCustomerWithSuccess() {
        CustomerFormDTO customerFormDTO = getCustomerFormDTO("John", "12345678901", "SP", 3500F, 25);
        Customer customer = getCustomer(false, "John", "12345678901", "SP", 3500F, 25);

        when(customerRepository.save(customer)).thenReturn(customer);

        CustomerDTO customerDTO = customerService.create(customerFormDTO);

        verify(customerRepository).save(customer);
        assertEquals(customerDTO.getName(), customerFormDTO.getName());
    }

    @Test
    public void shouldFindAllCustomersWithSuccess() {
        List<Customer> customers = List.of(getCustomer(true, "John", "12345678901", "SP", 3500F, 25));
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> customerPage = new PageImpl<>(customers, pageable, customers.size());

        when(customerRepository.findAll(any(Pageable.class))).thenReturn(customerPage);

        Page<CustomerDTO> response = customerService.findAll(pageable, CustomerParamsDTO.builder().build());
        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getContent().size()).isEqualTo(1);
        assertThat(response.getContent().get(0).getName()).isEqualTo("John");
        verify(customerRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void shouldFindCustomerById() {
        Customer customer = getCustomer(true, "John", "12345678901", "SP", 3500F, 25);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        CustomerDTO customerDTO = customerService.findOne(customer.getId());
        assertThat(customerDTO.getName()).isEqualTo("John");
        verify(customerRepository, times(1)).findById(customer.getId());
    }

    @Test
    public void shouldThrowNotFoundWhenFindCustomerById() {
        Customer customer = getCustomer(true, "John", "12345678901", "SP", 3500F, 25);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> customerService.findOne(customer.getId()))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(HttpStatus.NOT_FOUND.getReasonPhrase());
        verify(customerRepository, times(1)).findById(customer.getId());
    }

    @Test
    public void shouldUpdateCustomerById() {
        Customer customer = getCustomer(false, "John", "12345678901", "SP", 3500F, 25);
        CustomerFormDTO formDTO = getCustomerFormDTO("John", "12345678901", "SP", 3500F, 25);
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);

        CustomerDTO customerDTO = customerService.update(CUSTOMER_ID, formDTO);

        verify(customerRepository, times(1)).save(customer);
        assertEquals(customerDTO.getName(), formDTO.getName());
    }

    @Test
    public void shouldThrowNotFoundWhenUpdateCustomerId() {
        Customer customer = getCustomer(true, "John", "12345678901", "SP", 3500F, 25);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> customerService.update(customer.getId(), getCustomerFormDTO("John", "12345678901", "SP", 3500F, 25)))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(HttpStatus.NOT_FOUND.getReasonPhrase());
        verify(customerRepository, times(1)).findById(customer.getId());
    }

    @Test
    public void shouldDeleteCustomerById() {
        Customer customer = getCustomer(true, "John", "12345678901", "SP", 3500F, 25);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        customerService.delete(customer.getId());
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    public void shouldThrowNotFoundWhenDeleteCustomerId() {
        Customer customer = getCustomer(true, "John", "12345678901", "SP", 3500F, 25);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> customerService.delete(customer.getId()))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(HttpStatus.NOT_FOUND.getReasonPhrase());
        verify(customerRepository, times(1)).findById(customer.getId());
    }

    @Test
    public void shouldFindLoansForUserIncomeGreaterThan3000() {
        Customer customer = getCustomer(true, "John", "12345678901", "SP", 3500F, 25);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(loanRepository.findByLoanType(anyString())).thenReturn(LoanFixture.getLoan(LoanType.PERSONAL));
        CustomerLoanDTO customerLoanDTO = customerService.findLoansForUser(customer.getId());
        assertThat(customerLoanDTO.getCustomer()).isEqualTo("John");
        verify(customerRepository, times(1)).findById(customer.getId());
    }

    @Test
    public void shouldFindLoansForUserIncomeLessThan3000() {
        Customer customer = getCustomer(true, "John", "12345678901", "SP", 2500F, 25);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(loanRepository.findByLoanType(anyString())).thenReturn(LoanFixture.getLoan(LoanType.PERSONAL));
        CustomerLoanDTO customerLoanDTO = customerService.findLoansForUser(customer.getId());
        assertThat(customerLoanDTO.getCustomer()).isEqualTo("John");
        verify(customerRepository, times(1)).findById(customer.getId());
    }

    @Test
    public void shouldFindLoansForUserIncomeGreaterThan5000() {
        Customer customer = getCustomer(true, "John", "12345678901", "SP", 5500F, 25);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(loanRepository.findByLoanType(anyString())).thenReturn(LoanFixture.getLoan(LoanType.PERSONAL));
        CustomerLoanDTO customerLoanDTO = customerService.findLoansForUser(customer.getId());
        assertThat(customerLoanDTO.getCustomer()).isEqualTo("John");
        verify(customerRepository, times(1)).findById(customer.getId());
    }
}
