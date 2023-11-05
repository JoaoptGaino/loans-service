package com.br.joaoptgaino.loans.repository;

import com.br.joaoptgaino.loans.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
    @Query(value = "SELECT * FROM loans l WHERE l.type = ?1", nativeQuery = true)
    Loan findByLoanType(String type);
}
