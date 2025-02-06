package com.ashishsaranshakya.expensetracker.service;

import com.ashishsaranshakya.expensetracker.model.User;
import com.ashishsaranshakya.expensetracker.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getProfile(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public double updateBalance(String email, double balance) {
        User user = getProfile(email);
        user.setBalance(balance);
        userRepository.save(user);
        return balance;
    }
}
