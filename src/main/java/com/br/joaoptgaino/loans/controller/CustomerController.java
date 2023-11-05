package com.br.joaoptgaino.loans.controller;


import com.br.joaoptgaino.loans.common.CrudCommonController;
import com.br.joaoptgaino.loans.dto.customer.CustomerDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerFormDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerLoanDTO;
import com.br.joaoptgaino.loans.dto.customer.CustomerParamsDTO;
import com.br.joaoptgaino.loans.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController implements CrudCommonController<CustomerDTO, CustomerFormDTO, CustomerParamsDTO> {
    private final CustomerService customerService;

    @GetMapping
    @Override
    public ResponseEntity<Page<CustomerDTO>> findAll(@PageableDefault Pageable pageable, @RequestParam(required = false) CustomerParamsDTO params) {
        Page<CustomerDTO> customers = customerService.findAll(pageable, params);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<CustomerDTO> findOne(@PathVariable UUID id) {
        CustomerDTO customer = customerService.findOne(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    @Override
    public ResponseEntity<CustomerDTO> create(@RequestBody @Valid CustomerFormDTO formDTO) {
        CustomerDTO customer = customerService.create(formDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<CustomerDTO> update(@PathVariable UUID id, @RequestBody CustomerFormDTO formDTO) {
        CustomerDTO customer = customerService.update(id, formDTO);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/loans")
    public ResponseEntity<CustomerLoanDTO> findAllLoans(@PathVariable UUID id) {
        CustomerLoanDTO customers = customerService.findLoansForUser(id);
        return ResponseEntity.ok(customers);
    }
}
