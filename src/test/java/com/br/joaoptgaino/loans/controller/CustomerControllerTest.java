package com.br.joaoptgaino.loans.controller;


import com.br.joaoptgaino.loans.dto.customer.CustomerDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerFormDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerLoanDTO;
import com.br.joaoptgaino.loans.service.CustomerService;
import com.br.joaoptgaino.loans.service.LoanService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.br.joaoptgaino.loans.fixtures.CustomerFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class CustomerControllerTest {
    private MockMvc mockMvc;
    private final Gson gson = new GsonBuilder().create();

    private static final String BASE_URL = "/customers";
    @Mock
    private CustomerService customerService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new CustomerController(customerService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void findAllCustomersShouldReturnSuccessful() throws Exception {
        CustomerDTO customerDTO = getCustomerDTO("John", "123459680909", "SP", 3500F, 35);
        Page<CustomerDTO> customerResponse = new PageImpl<>(List.of(customerDTO));

        when(customerService.findAll(any(), any())).thenReturn(customerResponse);

        RequestBuilder request = MockMvcRequestBuilders.get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray());
    }

    @Test
    public void findOneCustomerShouldReturnSuccessful() throws Exception {
        CustomerDTO customerDTO = getCustomerDTO("John", "123459680909", "SP", 3500F, 35);
        when(customerService.findOne(CUSTOMER_ID)).thenReturn(customerDTO);

        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/%s", BASE_URL, CUSTOMER_ID))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("name")
                        .value(customerDTO.getName()));
    }

    @Test
    public void saveCustomerShouldReturnSuccessful() throws Exception {
        CustomerDTO customer = getCustomerDTO("John", "93557724085", "SP", 3500F, 35);
        CustomerFormDTO customerFormDTO = getCustomerFormDTO("John", "93557724085", "SP", 3500F, 35);

        String customerRequest = gson.toJson(customer);
        when(customerService.create(customerFormDTO)).thenReturn(customer);

        RequestBuilder request = MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerRequest)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value(customer.getName()));
    }

    @Test
    public void saveCustomerShouldThrowBadRequestInvalidCpf() throws Exception{
        CustomerDTO customer = getCustomerDTO("John", "221", "SP", 3500F, 35);
        CustomerFormDTO customerFormDTO = getCustomerFormDTO("John", "231", "SP", 3500F, 35);

        String customerRequest = gson.toJson(customer);
        when(customerService.create(customerFormDTO)).thenReturn(customer);

        RequestBuilder request = MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerRequest)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCustomerShouldReturnSuccessful() throws Exception {
        CustomerDTO customer = getCustomerDTO("John", "93557724085", "SP", 3500F, 35);
        CustomerFormDTO customerFormDTO = getCustomerFormDTO("John", "93557724085", "SP", 3500F, 35);

        String customerRequest = gson.toJson(customer);
        when(customerService.update(CUSTOMER_ID, customerFormDTO)).thenReturn(customer);

        RequestBuilder request = MockMvcRequestBuilders.put(String.format("%s/%s", BASE_URL, CUSTOMER_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerRequest)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(customer.getName()));
    }

    @Test
    public void deleteCustomerShouldReturnSuccessful() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete(String.format("%s/%s", BASE_URL, CUSTOMER_ID))
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    public void findAllLoansForUserShouldReturnSuccessful() throws Exception {
        CustomerLoanDTO customerLoanDTO = getCustomerLoanDTO("John");
        when(customerService.findLoansForUser(CUSTOMER_ID)).thenReturn(customerLoanDTO);
        RequestBuilder request = MockMvcRequestBuilders.get(String.format("%s/%s/loans", BASE_URL, CUSTOMER_ID))
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("customer").value(customerLoanDTO.getCustomer()))
                .andExpect(jsonPath("loans").isArray());
    }
}
