package com.ashishsaranshakya.expensetracker.controller;

import com.ashishsaranshakya.expensetracker.dto.AddIncomeRequest;
import com.ashishsaranshakya.expensetracker.model.Income;
import com.ashishsaranshakya.expensetracker.service.IncomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/incomes")
@Validated
public class IncomesController {

    private final IncomeService incomeService;

    public IncomesController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping
    public ResponseEntity<?> createIncome(Principal principal, @RequestBody @Valid AddIncomeRequest income) {
        return incomeService.createIncome(principal.getName(), income);
    }

    @GetMapping
    public ResponseEntity<?> getIncomes(Principal principal) {
        return incomeService.getIncomes(principal.getName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIncomeById(@PathVariable String id) {
        return incomeService.getIncomeById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateIncome(@PathVariable String id, @RequestBody @Valid Income income) {
        return incomeService.updateIncome(id, income);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIncome(@PathVariable String id) {
        return incomeService.deleteIncome(id);
    }
}
