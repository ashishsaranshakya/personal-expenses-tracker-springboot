package com.ashishsaranshakya.expensetracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UpdateIncomeRequest {
    private double amount;

    private String category;
    private Date date;
    private String description;
}
