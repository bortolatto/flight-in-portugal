package com.flightinportugal.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestEntityRepository extends CrudRepository<RequestEntity, Long> {

    List<RequestEntity> findAll();
}
