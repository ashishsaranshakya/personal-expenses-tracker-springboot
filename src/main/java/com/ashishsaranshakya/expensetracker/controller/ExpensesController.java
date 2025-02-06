package com.ashishsaranshakya.expensetracker.controller;

import com.ashishsaranshakya.expensetracker.dto.AddExpenseRequest;
import com.ashishsaranshakya.expensetracker.model.Expense;
import com.ashishsaranshakya.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/expenses")
@Validated
public class ExpensesController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpensesController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<?> createExpense(Principal principal, @RequestBody @Valid AddExpenseRequest expense) {
        return expenseService.createExpense(principal.getName(), expense);
    }

    @GetMapping
    public ResponseEntity<?> getExpenses(Principal principal) {
        return expenseService.getExpenses(principal.getName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable String id) {
        return expenseService.getExpenseById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable String id, @RequestBody @Valid Expense expense) {
        return expenseService.updateExpense(id, expense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable String id) {
        return expenseService.deleteExpense(id);
    }
}
