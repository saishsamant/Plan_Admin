package com.saish.plan_service.service;

import com.saish.plan_service.dto.PlanRequest;
import com.saish.plan_service.dto.PlanResponse;
import com.saish.plan_service.entity.PlanStatus;

import java.util.List;

public interface PlanService {

    PlanResponse createPlan(PlanRequest request);

    PlanResponse getPlanById(Long id);

    List<PlanResponse> getAllPlans();

    PlanResponse updatePlan(Long id, PlanRequest request);

    void deletePlan(Long id);

    List<PlanResponse> serchPlans(String planCode, String planName, PlanStatus status);
}