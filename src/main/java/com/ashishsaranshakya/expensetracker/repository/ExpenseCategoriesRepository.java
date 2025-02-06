package com.ashishsaranshakya.expensetracker.repository;

import com.ashishsaranshakya.expensetracker.model.ExpenseCategories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseCategoriesRepository extends MongoRepository<ExpenseCategories, String> {
    ExpenseCategories findByUserId(String userId);
}
