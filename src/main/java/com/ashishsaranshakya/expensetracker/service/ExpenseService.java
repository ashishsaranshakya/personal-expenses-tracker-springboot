package com.ashishsaranshakya.expensetracker.service;

import com.ashishsaranshakya.expensetracker.dto.AddExpenseRequest;
import com.ashishsaranshakya.expensetracker.dto.UpdateExpenseRequest;
import com.ashishsaranshakya.expensetracker.model.*;
import com.ashishsaranshakya.expensetracker.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ExpenseCategoriesRepository expenseCategoriesRepository;

    public ExpenseService(ExpenseRepository expenseRepository,
                          UserRepository userRepository,
                          ExpenseCategoriesRepository expenseCategoriesRepository,
                          ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.expenseCategoriesRepository = expenseCategoriesRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Transactional
    public ResponseEntity<?> createExpense(String userId, AddExpenseRequest expense) {
        ExpenseCategories categories = expenseCategoriesRepository.findByUserId(userId);
        expense.setUserId(userId);

        Optional<ExpenseCategory> category = categories.getCategories().stream()
                .filter(cat -> cat.getId().equals(expense.getCategory()))
                .findFirst();

        if (category.isEmpty()) {
            return ResponseEntity.badRequest().body("Category not found");
        }

        User user = userRepository.findById(expense.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        user.setBalance(user.getBalance() - expense.getAmount());
        userRepository.save(user);

        expenseRepository.save(expense.mapToExpenseModel(category.get()));

        return ResponseEntity.ok("Expense created successfully.");
    }

    public ResponseEntity<?> getExpenses(String userId) {
        List<Expense> expenses = expenseRepository.findByUserId(userId);

        ExpenseCategories categories = expenseCategoriesRepository.findByUserId(userId);

        List<Map<String, Object>> expensesWithCategory = expenses.stream()
                .sorted((e1, e2) -> e2.getDate().compareTo(e1.getDate()))
                .map(expense -> {
                    ExpenseCategory category = categories.getCategories().stream()
                            .filter(cat -> cat.getId().equals(expense.getCategoryId().getId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Category not found"));

                    Map<String, Object> expenseMap = new HashMap<>();
                    expenseMap.put("_id", expense.getId());
                    expenseMap.put("amount", expense.getAmount());
                    expenseMap.put("date", expense.getDate());
                    expenseMap.put("category", category.getName());
                    expenseMap.put("categoryId", category.getId());
                    expenseMap.put("description", expense.getDescription());
                    return expenseMap;
                })
                .toList();

        return ResponseEntity.ok(Map.of("success", true, "expenses", expensesWithCategory));
    }

    public ResponseEntity<?> getExpenseById(String id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
        return ResponseEntity.ok(expense);
    }

    @Transactional
    public ResponseEntity<?> updateExpense(String id, UpdateExpenseRequest updatedExpense) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
        User user = userRepository.findById(expense.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        if(updatedExpense.getAmount() != expense.getAmount()) {
            user.setBalance(user.getBalance() + expense.getAmount() - updatedExpense.getAmount());
            userRepository.save(user);
            expense.setAmount(updatedExpense.getAmount());
        }

        if(updatedExpense.getDate() != null) expense.setDate(updatedExpense.getDate());
        if(updatedExpense.getCategory() != null){
            ExpenseCategory category = expenseCategoryRepository.findById(updatedExpense.getCategory()).orElseThrow(() -> new RuntimeException("Category not found"));
            expense.setCategoryId(category);
        }
        if(updatedExpense.getDescription() != null) expense.setDescription(updatedExpense.getDescription());

        expenseRepository.save(expense);

        return ResponseEntity.ok("Expense updated successfully.");
    }

    @Transactional
    public ResponseEntity<?> deleteExpense(String id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
        User user = userRepository.findById(expense.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        user.setBalance(user.getBalance() + expense.getAmount());
        userRepository.save(user);

        expenseRepository.delete(expense);

        return ResponseEntity.ok("Expense deleted successfully.");
    }
}
