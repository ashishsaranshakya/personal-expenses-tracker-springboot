package com.ashishsaranshakya.expensetracker.controller;

import com.ashishsaranshakya.expensetracker.model.User;
import com.ashishsaranshakya.expensetracker.service.UserService;
import com.ashishsaranshakya.expensetracker.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final CategoryService categoryService;

    public UserController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<User> getProfile(Principal principal) {
        return ResponseEntity.ok(userService.getProfile(principal.getName()));
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateBalance(@RequestBody Map<String, Double> balanceData, Principal principal) {
        double balance = balanceData.get("balance");
        return ResponseEntity.ok(Map.of("success", true, "balance", userService.updateBalance(principal.getName(), balance)));
    }

    @GetMapping("/categories")
    public ResponseEntity<Map<String, Object>> getCategories(Principal principal) {
        return ResponseEntity.ok(categoryService.getCategories(principal.getName()));
    }

    @PostMapping("/categories/income")
    public ResponseEntity<Map<String, String>> addIncomeCategory(@RequestBody @Valid Map<String, String> request, Principal principal) {
        categoryService.addIncomeCategory(principal.getName(), request.get("name"));
        return ResponseEntity.ok(Map.of("success", "Category added successfully"));
    }

    @PostMapping("/categories/expense")
    public ResponseEntity<Map<String, String>> addExpenseCategory(@RequestBody @Valid Map<String, String> request, Principal principal) {
        categoryService.addExpenseCategory(principal.getName(), request.get("name"));
        return ResponseEntity.ok(Map.of("success", "Category added successfully"));
    }

    @DeleteMapping("/categories/income/{id}")
    public ResponseEntity<Map<String, String>> deleteIncomeCategory(@PathVariable String id, Principal principal) {
        categoryService.deleteIncomeCategory(principal.getName(), id);
        return ResponseEntity.ok(Map.of("success", "Category deleted successfully"));
    }

    @DeleteMapping("/categories/expense/{id}")
    public ResponseEntity<Map<String, String>> deleteExpenseCategory(@PathVariable String id, Principal principal) {
        categoryService.deleteExpenseCategory(principal.getName(), id);
        return ResponseEntity.ok(Map.of("success", "Category deleted successfully"));
    }
}
