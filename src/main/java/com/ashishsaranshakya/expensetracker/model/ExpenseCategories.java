package com.ashishsaranshakya.expensetracker.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "expense_categories_by_user")
@Getter
@Setter
public class ExpenseCategories {
    @Id
    private String id;
    private String userId;

    @DBRef
    private List<ExpenseCategory> categories;
}
