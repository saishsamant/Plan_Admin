package com.saish.plan_service.controller;

import com.saish.plan_service.dto.PlanRequest;
import com.saish.plan_service.dto.PlanResponse;
import com.saish.plan_service.entity.PlanStatus;
import com.saish.plan_service.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;


    // CREATE PLAN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlanResponse> createPlan(
            @Valid @RequestBody PlanRequest request) {

        PlanResponse response = planService.createPlan(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET ALL PLANS
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<PlanResponse>> getAllPlans() {

        return ResponseEntity.ok(
                planService.getAllPlans()
        );
    }

    //Get By planCode+PlanName+Status

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<PlanResponse>> getPlans(
            @RequestParam(required = false) String planCode,
            @RequestParam(required = false) String planName,
            @RequestParam(required = false) PlanStatus status){



        return ResponseEntity.ok(planService.serchPlans(planCode,planName,status));
    }


    // GET PLAN BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PlanResponse> getPlanById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                planService.getPlanById(id)
        );
    }

    // UPDATE PLAN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlanResponse> updatePlan(
            @PathVariable Long id,
            @Valid @RequestBody PlanRequest request) {

        return ResponseEntity.ok(
                planService.updatePlan(id, request)
        );
    }

    // DELETE PLAN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN','USER')")
    public ResponseEntity<Void> deletePlan(
            @PathVariable Long id) {

        planService.deletePlan(id);

        return ResponseEntity.noContent().build();
    }
}