package com.ashishsaranshakya.expensetracker.dto;

import com.ashishsaranshakya.expensetracker.model.Income;
import com.ashishsaranshakya.expensetracker.model.IncomeCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AddIncomeRequest {
    private String userId;
    private double amount;

    private String category;
    private Date date;
    private String description;

    public Income mapToIncomeModel(IncomeCategory category) {
        Income income = new Income();
        income.setAmount(this.amount);
        income.setCategoryId(category);
        income.setDate(this.date);
        income.setDescription(this.description);
        return income;
    }
}
