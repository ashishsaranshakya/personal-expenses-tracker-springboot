package com.ashishsaranshakya.expensetracker.repository;

import com.ashishsaranshakya.expensetracker.model.Income;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends MongoRepository<Income, String> {
    List<Income> findByCategoryId(String categoryId);
    List<Income> findByUserId(String userId);

}
