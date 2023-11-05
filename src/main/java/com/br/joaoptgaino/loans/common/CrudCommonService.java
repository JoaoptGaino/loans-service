package com.br.joaoptgaino.loans.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CrudCommonService<E, F, P> {
    Page<E> findAll(Pageable pageable, P paramsDTO);

    E findOne(UUID id);

    E create(F formDTO);

    E update(UUID id, F formDTO);

    void delete(UUID id);
}
