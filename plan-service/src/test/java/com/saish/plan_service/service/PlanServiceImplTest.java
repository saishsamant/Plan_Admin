package com.saish.plan_service.service;

import com.saish.plan_service.dto.PlanRequest;
import com.saish.plan_service.dto.PlanResponse;
import com.saish.plan_service.entity.Plan;
import com.saish.plan_service.entity.PlanStatus;
import com.saish.plan_service.exception.DuplicatePlanCodeException;
import com.saish.plan_service.exception.ResourceNotFoundException;
import com.saish.plan_service.mapper.PlanMapper;
import com.saish.plan_service.repository.PlanRepository;
import com.saish.plan_service.service.implementation.PlanServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanServiceImplTest {

    @Mock
    private PlanRepository planRepository;

    @Mock
    private PlanMapper planMapper;

    @InjectMocks
    private PlanServiceImpl planService;

    @Test
    void shouldReturnAllPlans (){
        // Arrange
        Plan plan1 = new Plan();
        plan1.setId(1L);
        plan1.setPlanCode("RET-001");

        Plan plan2 = new Plan();
        plan2.setId(2L);
        plan2.setPlanCode("RET-002");


        PlanResponse response1 = PlanResponse.builder()
                .id(1L)
                .planCode("RTE-001").build();
        PlanResponse response2 = PlanResponse.builder()
                .id(2L)
                .planCode("RTE-002").build();

        when(planRepository.findAll())
                .thenReturn(List.of(plan1, plan2));

        when(planMapper.toResponse(plan1))
                .thenReturn(response1);

        when(planMapper.toResponse(plan2))
                .thenReturn(response2);

        // Act
        List<PlanResponse> result = planService.getAllPlans();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("RTE-001", result.get(0).getPlanCode());
        assertEquals(1, result.get(0).getId());

        assertEquals("RTE-002", result.get(1).getPlanCode());
        assertEquals(2, result.get(1).getId());

        verify(planRepository).findAll();
        verify(planRepository, times(1)).findAll();
        verify(planMapper).toResponse(plan1);
        verify(planMapper).toResponse(plan2);


    }

    @Test
    void shouldReturnEmptyListWhenNoPlansExist(){

        when(planRepository.findAll()).thenReturn(List.of());
        List<PlanResponse> result = planService.getAllPlans();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(planRepository).findAll();

        verifyNoInteractions(planMapper);
    }

    //Create Plan Successfully Test

    @Test
    void shouldCreatePlanSuccessfully() {

        // Arrange
        PlanRequest request = new PlanRequest();
        request.setPlanCode("RET-001");
        request.setPlanName("Retirement Plan");
        request.setDescription("Retirement plan description");
        request.setStatus(PlanStatus.ACTIVE);

        Plan plan = new Plan();
        plan.setId(1L);
        plan.setPlanCode("RET-001");
        plan.setPlanName("Retirement Plan");

        Plan savedPlan = new Plan();
        savedPlan.setId(1L);
        savedPlan.setPlanCode("RET-001");
        savedPlan.setPlanName("Retirement Plan");

        PlanResponse response = PlanResponse.builder()
                .id(1L)
                .planCode("RET-001")
                .planName("Retirement Plan")
                .description("Retirement plan description")
                .status(PlanStatus.ACTIVE)
                .build();

        when(planRepository.existsByPlanCode("RET-001"))
                .thenReturn(false);

        when(planMapper.toEntity(request))
                .thenReturn(plan);

        when(planRepository.save(plan))
                .thenReturn(savedPlan);

        when(planMapper.toResponse(savedPlan))
                .thenReturn(response);

        // Act
        PlanResponse result = planService.createPlan(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("RET-001", result.getPlanCode());
        assertEquals("Retirement Plan", result.getPlanName());

        verify(planRepository).existsByPlanCode("RET-001");
        verify(planMapper).toEntity(request);
        verify(planRepository).save(plan);
        verify(planMapper).toResponse(savedPlan);
    }

}