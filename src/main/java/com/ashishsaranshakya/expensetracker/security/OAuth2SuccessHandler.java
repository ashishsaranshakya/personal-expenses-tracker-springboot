package com.ashishsaranshakya.expensetracker.security;

import com.ashishsaranshakya.expensetracker.model.*;
import com.ashishsaranshakya.expensetracker.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final IncomeCategoriesRepository incomeCategoriesRepository;
    private final ExpenseCategoriesRepository expenseCategoriesRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public OAuth2SuccessHandler(UserRepository userRepository, JwtUtil jwtUtil,
                                IncomeCategoriesRepository incomeCategoriesRepository,
                                ExpenseCategoriesRepository expenseCategoriesRepository,
                                IncomeCategoryRepository incomeCategoryRepository,
                                ExpenseCategoryRepository expenseCategoryRepository) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.incomeCategoriesRepository = incomeCategoriesRepository;
        this.expenseCategoriesRepository = expenseCategoriesRepository;
        this.incomeCategoryRepository = incomeCategoryRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        Optional<User> existingUser = userRepository.findByEmail(email);

        User user = existingUser.orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setProvider("google");
            newUser.setProviderId(oAuth2User.getAttribute("sub"));
            newUser.setProfilePictureUrl(picture);
            newUser = userRepository.save(newUser);

            assignDefaultCategories(newUser);

            return newUser;
        });

        String token = jwtUtil.generateToken(user.getId());

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 10);
        response.addCookie(cookie);

        response.sendRedirect("http://localhost:3000/");
    }

    private void assignDefaultCategories(User user) {
        ExpenseCategory defaultExpenseCategory = expenseCategoryRepository.save(new ExpenseCategory("Misc"));

        ExpenseCategories expenseCategories = new ExpenseCategories();
        expenseCategories.setUserId(user.getId());
        expenseCategories.setCategories(List.of(defaultExpenseCategory));
        expenseCategoriesRepository.save(expenseCategories);

        IncomeCategory defaultIncomeCategory = incomeCategoryRepository.save(new IncomeCategory("Misc"));

        IncomeCategories incomeCategories = new IncomeCategories();
        incomeCategories.setUserId(user.getId());
        incomeCategories.setCategories(List.of(defaultIncomeCategory));
        incomeCategoriesRepository.save(incomeCategories);
    }

}
