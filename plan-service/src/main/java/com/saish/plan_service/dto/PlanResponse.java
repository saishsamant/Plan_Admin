package com.saish.plan_service.dto;

import com.saish.plan_service.entity.PlanStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PlanResponse {

    private Long id;
    private String planCode;
    private String planName;
    private String description;
    private PlanStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}