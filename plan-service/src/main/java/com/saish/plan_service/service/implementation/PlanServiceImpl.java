package com.saish.plan_service.service.implementation;

import com.saish.plan_service.dto.PlanRequest;
import com.saish.plan_service.dto.PlanResponse;
import com.saish.plan_service.entity.Plan;
import com.saish.plan_service.entity.PlanStatus;
import com.saish.plan_service.exception.DuplicatePlanCodeException;
import com.saish.plan_service.exception.ResourceNotFoundException;
import com.saish.plan_service.mapper.PlanMapper;
import com.saish.plan_service.repository.PlanRepository;
import com.saish.plan_service.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final PlanMapper planMapper;

    @Override
    public PlanResponse createPlan(PlanRequest request) {

        log.info("Creating plan with planCode: {}", request.getPlanCode());

        if (planRepository.existsByPlanCode(request.getPlanCode())) {

            log.warn("Plan creation failed. Duplicate planCode: {}",
                    request.getPlanCode());

            throw new DuplicatePlanCodeException(
                    "Plan code already exists: " + request.getPlanCode()
            );
        }

        Plan plan = planMapper.toEntity(request);

        Plan savedPlan = planRepository.save(plan);

        log.info("Plan created successfully with id: {}",
                savedPlan.getId());

        return planMapper.toResponse(savedPlan);
    }

    @Override
    public PlanResponse getPlanById(Long id) {

        log.info("Fetching plan with id: {}", id);

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Plan not found with id: {}", id);

                    return new ResourceNotFoundException(
                            "Plan not found with id: " + id
                    );
                });

        return planMapper.toResponse(plan);
    }

    @Override
    public List<PlanResponse> getAllPlans() {
        log.info("Getting all Plans:{}");

        return planRepository.findAll()
                .stream()
                .map(planMapper::toResponse)
                .toList();
    }

    @Override
    public List<PlanResponse> serchPlans(String planCode, String planName, PlanStatus status) {
        return planRepository.searchPlans(planCode,planName,status)
                .stream()
                .map(planMapper::toResponse)
                .toList();
    }

    @Override
    public PlanResponse updatePlan(Long id, PlanRequest request) {

        log.info("Updating plan with id: {}", id);

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Plan not found for update. id: {}", id);

                    return new ResourceNotFoundException(
                            "Plan not found with id: " + id
                    );
                });

        if (!plan.getPlanCode().equals(request.getPlanCode())
                && planRepository.existsByPlanCode(request.getPlanCode())) {

            log.warn("Update failed. Duplicate planCode: {}",
                    request.getPlanCode());

            throw new DuplicatePlanCodeException(
                    "Plan code already exists: " + request.getPlanCode()
            );
        }

        plan.setPlanCode(request.getPlanCode());
        plan.setPlanName(request.getPlanName());
        plan.setDescription(request.getDescription());
        plan.setStatus(request.getStatus());

        Plan updatedPlan = planRepository.save(plan);

        log.info("Plan updated successfully. id: {}", id);

        return planMapper.toResponse(updatedPlan);
    }

    @Override
    public void deletePlan(Long id) {

        log.info("Deleting plan with id: {}", id);

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Plan not found for deletion. id: {}", id);

                    return new ResourceNotFoundException(
                            "Plan not found with id: " + id
                    );
                });

        planRepository.delete(plan);

        log.info("Plan deleted successfully. id: {}", id);
    }
}