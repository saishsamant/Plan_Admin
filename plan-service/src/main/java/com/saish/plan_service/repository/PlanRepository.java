package com.saish.plan_service.repository;

import com.saish.plan_service.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    boolean existsByPlanCode(String planCode);
}