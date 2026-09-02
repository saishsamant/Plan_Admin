package com.saish.plan_service.dto;

import com.saish.plan_service.entity.PlanStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanRequest {

    @NotBlank(message = "Plan code is required")
    @Size(max = 50, message = "Plan code must not exceed 50 characters")
    private String planCode;

    @NotBlank(message = "Plan name is required")
    @Size(max = 100, message = "Plan name must not exceed 100 characters")
    private String planName;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Status is required")
    private PlanStatus status;
}