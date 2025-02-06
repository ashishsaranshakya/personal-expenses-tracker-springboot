package com.ashishsaranshakya.expensetracker.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "expenses")
@Getter
@Setter
public class Expense {
    @Id
    private String id;
    private String userId;
    private double amount;
    private Date date;
    @DBRef
    private ExpenseCategory categoryId;
    private String description;
}
