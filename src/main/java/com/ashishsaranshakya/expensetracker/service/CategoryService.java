package com.ashishsaranshakya.expensetracker.service;

import com.ashishsaranshakya.expensetracker.model.*;
import com.ashishsaranshakya.expensetracker.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final IncomeCategoriesRepository incomeCategoriesRepository;
    private final ExpenseCategoriesRepository expenseCategoriesRepository;
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    public CategoryService(IncomeCategoriesRepository incomeCategoriesRepository, ExpenseCategoriesRepository expenseCategoriesRepository,
                           IncomeRepository incomeRepository, ExpenseRepository expenseRepository) {
        this.incomeCategoriesRepository = incomeCategoriesRepository;
        this.expenseCategoriesRepository = expenseCategoriesRepository;
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }

    public Map<String, Object> getCategories(String userId) {
        IncomeCategories incomeCategories = incomeCategoriesRepository.findByUserId(userId);
        ExpenseCategories expenseCategories = expenseCategoriesRepository.findByUserId(userId);

        List<Map<String, Object>> incomeList = incomeCategories.getCategories().stream()
                .map(cat -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("_id", cat.getId());
                    map.put("name", cat.getName());
                    map.put("amount", calculateTotalIncomeAmount(cat.getId()));
                    return map;
                })
                .toList();

        List<Map<String, Object>> expenseList = expenseCategories.getCategories()
                .stream()
                .map(cat -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("_id", cat.getId());
                    map.put("name", cat.getName());
                    map.put("amount", calculateTotalExpenseAmount(cat.getId()));
                    return map;
                })
                .toList();

        return Map.of("success", true, "incomeCategories", Map.of("categories", incomeList), "expenseCategories", Map.of("categories", expenseList));
    }

    private double calculateTotalIncomeAmount(String categoryId) {
        List<Income> incomes = incomeRepository.findByCategoryId(categoryId);
        return incomes.stream().mapToDouble(Income::getAmount).sum();
    }

    private double calculateTotalExpenseAmount(String categoryId) {
        List<Expense> expenses = expenseRepository.findByCategoryId(categoryId);
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public void addIncomeCategory(String userId, String name) {
        IncomeCategories categories = incomeCategoriesRepository.findByUserId(userId);
        categories.getCategories().add(new IncomeCategory(name));
        incomeCategoriesRepository.save(categories);
    }

    public void addExpenseCategory(String userId, String name) {
        ExpenseCategories categories = expenseCategoriesRepository.findByUserId(userId);
        categories.getCategories().add(new ExpenseCategory(name));
        expenseCategoriesRepository.save(categories);
    }

    public void deleteIncomeCategory(String userId, String id) {
        IncomeCategories categories = incomeCategoriesRepository.findByUserId(userId);
        categories.setCategories(categories.getCategories().stream()
                .filter(cat -> !cat.getId().equals(id))
                .collect(Collectors.toList()));
        incomeCategoriesRepository.save(categories);
    }

    public void deleteExpenseCategory(String userId, String id) {
        ExpenseCategories categories = expenseCategoriesRepository.findByUserId(userId);
        categories.setCategories(categories.getCategories().stream()
                .filter(cat -> !cat.getId().equals(id))
                .collect(Collectors.toList()));
        expenseCategoriesRepository.save(categories);
    }
}