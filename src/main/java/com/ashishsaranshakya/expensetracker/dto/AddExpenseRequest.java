package com.ashishsaranshakya.expensetracker.dto;

import com.ashishsaranshakya.expensetracker.model.Expense;
import com.ashishsaranshakya.expensetracker.model.ExpenseCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AddExpenseRequest {
    private String userId;
    private double amount;

    private String category;
    private Date date;
    private String description;

    public Expense mapToExpenseModel(ExpenseCategory category) {
        Expense expense = new Expense();
        expense.setAmount(this.amount);
        expense.setCategoryId(category);
        expense.setDate(this.date);
        expense.setDescription(this.description);
        return expense;
    }
}
