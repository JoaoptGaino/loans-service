package com.br.joaoptgaino.loans.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CustomerDTO {
    private UUID id;
    private String name;
    private String cpf;
    private Integer age;
    private Float income;
    private String location;
}
