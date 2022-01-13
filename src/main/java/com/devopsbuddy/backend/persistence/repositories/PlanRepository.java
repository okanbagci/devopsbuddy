package com.devopsbuddy.backend.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import com.devopsbuddy.backend.persistence.domain.backend.Plan;

public interface PlanRepository extends CrudRepository<Plan, Integer> {

}
