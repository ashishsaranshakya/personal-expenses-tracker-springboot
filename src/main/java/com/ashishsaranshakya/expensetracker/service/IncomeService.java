package com.ashishsaranshakya.expensetracker.service;

import com.ashishsaranshakya.expensetracker.dto.AddIncomeRequest;
import com.ashishsaranshakya.expensetracker.dto.UpdateIncomeRequest;
import com.ashishsaranshakya.expensetracker.model.*;
import com.ashishsaranshakya.expensetracker.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import jakarta.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;

    private final IncomeCategoriesRepository incomeCategoriesRepository;

    private final UserRepository userRepository;

    public IncomeService(IncomeRepository incomeRepository, IncomeCategoriesRepository incomeCategoriesRepository,
                         UserRepository userRepository, IncomeCategoryRepository incomeCategoryRepository) {
        this.incomeRepository = incomeRepository;
        this.incomeCategoriesRepository = incomeCategoriesRepository;
        this.userRepository = userRepository;
        this.incomeCategoryRepository = incomeCategoryRepository;
    }

    @Transactional
    public ResponseEntity<String> createIncome(String userId, AddIncomeRequest income) {
        IncomeCategories categories = incomeCategoriesRepository.findByUserId(userId);
        income.setUserId(userId);

        Optional<IncomeCategory> category = categories.getCategories().stream()
                .filter(cat -> cat.getId().equals(income.getCategory()))
                .findFirst();

        if (category.isEmpty()) {
            return ResponseEntity.badRequest().body("Category not found");
        }

        incomeRepository.save(income.mapToIncomeModel(category.get()));

        User user = userRepository.findById(income.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        user.setBalance(user.getBalance() + income.getAmount());
        userRepository.save(user);

        return ResponseEntity.ok("Income created successfully.");
    }

    public ResponseEntity<?> getIncomes(String userId) {
        List<Income> incomes = incomeRepository.findByUserId(userId);

        IncomeCategories categories = incomeCategoriesRepository.findByUserId(userId);

        List<Map<String, Object>> incomesWithCategory = incomes.stream()
                .sorted((e1, e2) -> e2.getDate().compareTo(e1.getDate()))
                .map(income -> {
                    IncomeCategory category = categories.getCategories().stream()
                            .filter(cat -> cat.getId().equals(income.getCategoryId().getId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Category not found"));

                    Map<String, Object> incomeMap = new HashMap<>();
                    incomeMap.put("_id", income.getId());
                    incomeMap.put("amount", income.getAmount());
                    incomeMap.put("date", income.getDate());
                    incomeMap.put("category", category.getName());
                    incomeMap.put("categoryId", category.getId());
                    incomeMap.put("description", income.getDescription());
                    return incomeMap;
                })
                .toList();

        return ResponseEntity.ok(Map.of("success", true, "incomes", incomesWithCategory));
    }

    public ResponseEntity<?> getIncomeById(String id) {
        Optional<Income> income = incomeRepository.findById(id);
        if (income.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(income.get());
    }

    @Transactional
    public ResponseEntity<?> updateIncome(String id, UpdateIncomeRequest updatedIncome) {
        Income income = incomeRepository.findById(id).orElseThrow(() -> new RuntimeException("Income not found"));
        User user = userRepository.findById(income.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        if(updatedIncome.getAmount() != income.getAmount()) {
            user.setBalance(user.getBalance() - income.getAmount() + updatedIncome.getAmount());
            userRepository.save(user);
            income.setAmount(updatedIncome.getAmount());
        }

        if(updatedIncome.getDate() != null) income.setDate(updatedIncome.getDate());
        if(updatedIncome.getCategory() != null){
            IncomeCategory category = incomeCategoryRepository.findById(updatedIncome.getCategory()).orElseThrow(() -> new RuntimeException("Category not found"));
            income.setCategoryId(category);
        }
        if(updatedIncome.getDescription() != null) income.setDescription(updatedIncome.getDescription());

        incomeRepository.save(income);

        return ResponseEntity.ok("Income updated successfully.");
    }

    @Transactional
    public ResponseEntity<?> deleteIncome(String id) {
        Income income = incomeRepository.findById(id).orElseThrow(() -> new RuntimeException("Income not found"));
        User user = userRepository.findById(income.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        user.setBalance(user.getBalance() - income.getAmount());
        userRepository.save(user);

        incomeRepository.delete(income);

        return ResponseEntity.ok("Income deleted successfully.");
    }
}
