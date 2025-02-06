package com.ashishsaranshakya.expensetracker.repository;

import com.ashishsaranshakya.expensetracker.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {
    List<Expense> findByCategoryId(String categoryId);
}
