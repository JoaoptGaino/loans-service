package com.br.joaoptgaino.loans.repository;

import com.br.joaoptgaino.loans.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
