package com.br.joaoptgaino.loans.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CrudCommonController<E, F, P> {
    ResponseEntity<Page<E>> findAll(Pageable pageable, P params);

    ResponseEntity<E> findOne(UUID id);

    ResponseEntity<E> create(F formDTO);

    ResponseEntity<E> update(UUID id, F formDTO);

    ResponseEntity<Void> delete(UUID id);
}
