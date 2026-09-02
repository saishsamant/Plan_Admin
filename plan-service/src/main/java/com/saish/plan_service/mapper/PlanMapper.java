package com.saish.plan_service.mapper;

import com.saish.plan_service.dto.PlanRequest;
import com.saish.plan_service.dto.PlanResponse;
import com.saish.plan_service.entity.Plan;
import org.springframework.stereotype.Component;

@Component
public class PlanMapper {

    public Plan toEntity(PlanRequest request) {
        return Plan.builder()
                .planCode(request.getPlanCode())
                .planName(request.getPlanName())
                .description(request.getDescription())
                .status(request.getStatus())
                .build();
    }

    public PlanResponse toResponse(Plan plan) {
        return PlanResponse.builder()
                .id(plan.getId())
                .planCode(plan.getPlanCode())
                .planName(plan.getPlanName())
                .description(plan.getDescription())
                .status(plan.getStatus())
                .createdAt(plan.getCreatedAt())
                .updatedAt(plan.getUpdatedAt())
                .build();
    }
}