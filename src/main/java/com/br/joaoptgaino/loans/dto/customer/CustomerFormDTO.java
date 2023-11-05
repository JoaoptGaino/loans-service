package com.br.joaoptgaino.loans.dto.customer;

import com.br.joaoptgaino.loans.validation.annotation.CpfValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerFormDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Cpf is mandatory")
    @CpfValidation
    private String cpf;

    @NotNull(message = "Age is mandatory")
    private Integer age;

    @NotNull(message = "Income is mandatory")
    private Float income;

    @NotBlank(message = "Location is mandatory")
    @Size(min = 2, max = 2, message = "Location must have 2 characters")
    private String location;
}
