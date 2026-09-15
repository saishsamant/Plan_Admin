package com.saish.plan_service.repository;

import com.saish.plan_service.entity.Plan;
import com.saish.plan_service.entity.PlanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    boolean existsByPlanCode(String planCode);

    @Query("""
    SELECT p FROM Plan p
    WHERE (:planCode IS NULL OR p.planCode = :planCode)
      AND (:planName IS NULL OR p.planName = :planName)
      AND (:status IS NULL OR p.status = :status)
""")
    List<Plan> searchPlans(
            @Param("planCode") String planCode,
            @Param("planName") String planName,
            @Param("status") PlanStatus status
    );

}