package com.ashishsaranshakya.expensetracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "expense_categories")
@Getter
@Setter
public class ExpenseCategory {
    @Id
    private String id;
    private String name;

    public ExpenseCategory(String name) {
        this.name = name;
    }
}
