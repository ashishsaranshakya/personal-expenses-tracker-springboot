package com.ashishsaranshakya.expensetracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "users")
@Getter
@Setter
public class User {
    @Id
    private String id;
    private String provider;
    private String providerId;
    private String email;
    private String name;
    private String profilePictureUrl;
    private double balance = 0.0;
}
