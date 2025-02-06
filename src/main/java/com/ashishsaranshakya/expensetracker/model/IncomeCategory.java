package com.ashishsaranshakya.expensetracker.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "income_categories")
@Getter
@Setter
public class IncomeCategory {
    @Id
    private String id;
    private String name;

    public IncomeCategory(String name) {
        this.name = name;
    }
}
