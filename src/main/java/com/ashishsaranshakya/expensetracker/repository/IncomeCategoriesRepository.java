package com.ashishsaranshakya.expensetracker.repository;

import com.ashishsaranshakya.expensetracker.model.IncomeCategories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeCategoriesRepository extends MongoRepository<IncomeCategories, String> {
    IncomeCategories findByUserId(String userId);
}
