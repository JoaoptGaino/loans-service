package com.br.joaoptgaino.loans.controller;


import com.br.joaoptgaino.loans.dto.loans.LoanDTO;
import com.br.joaoptgaino.loans.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/loans")
record LoanController(LoanService loanService) {
    @GetMapping
    public ResponseEntity<List<LoanDTO>> findAll() {
        return ResponseEntity.ok(loanService.findAll());
    }
}
