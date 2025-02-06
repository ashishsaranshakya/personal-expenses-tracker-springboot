package com.ashishsaranshakya.expensetracker.repository;

import com.ashishsaranshakya.expensetracker.model.IncomeCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeCategoryRepository extends MongoRepository<IncomeCategory, String> {
}
