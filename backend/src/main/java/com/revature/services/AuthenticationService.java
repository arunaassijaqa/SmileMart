package com.revature.services;

import com.revature.daos.UserDAO;
import com.revature.models.User;
import com.revature.models.dtos.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private UserDAO userDAO;

    // Dependecy Injection
    @Autowired
    public AuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // Register a user
    public String registerUser(UserRegistrationDTO userRegistrationDTO) throws IllegalArgumentException{

        // Checks if name is empty
        if (userRegistrationDTO.getName().isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank!");
        }

        // Ensures password is:
        //    - At least one digit (0-9)
        //    - At least one lowercase letter (a-z)
        //    - At least one uppercase letter (A-Z)
        //    - At least one special character (*.!@#$%^&(){}[]:;<>,.?/~_+-=|\])
        //    - At least 8 characters long, but no more than 32
        String passwordRegex = "(?=.*[a-z])(?=. *[A-Z])(?=. *[0-9])(?=. *[^A-Za-z0-9])(?=. {8,}) [3]";

        if (!userRegistrationDTO.getPassword().matches(passwordRegex)) {
            throw new IllegalArgumentException("Invalid password!");
        }

        if (userRegistrationDTO.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be blank!");
        }

        // New user object
        User user = new User();

        // Set user details from DTO using setters
        user.setName(userRegistrationDTO.getName());
        user.setPassword(userRegistrationDTO.getPassword());
        user.setEmail(userRegistrationDTO.getEmail());

        User newUser = userDAO.save(user);

        return "User " + newUser.getName() + " was registered successfully!";


    }
}
