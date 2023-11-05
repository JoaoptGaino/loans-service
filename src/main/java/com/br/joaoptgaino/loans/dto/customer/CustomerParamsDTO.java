package com.br.joaoptgaino.loans.dto.customer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerParamsDTO {
    private String name;
    private String cpf;
    private Integer age;
    private Float income;
    private String location;
}
