package com.swm.smartwaste.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.swm.smartwaste.Dao.UserDao;
import com.swm.smartwaste.Entity.User;
import com.swm.smartwaste.Enum.UserRole;
import com.swm.smartwaste.Security.JwtUtil;
import com.swm.smartwaste.dto.ResponseStructure;

@Service
public class UserServices {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<ResponseStructure<User>> addUser(User user) {
        ResponseStructure<User> response = new ResponseStructure<>();

        if (user.getName() == null || user.getName().isBlank()
                || user.getEmail() == null || user.getEmail().isBlank()
                || user.getPassword() == null || user.getPassword().isBlank()) {

            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Name, email, and password are required");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        String email = user.getEmail().trim().toLowerCase();

        if (userDao.existsByEmail(email)) {
            response.setStatusCode(HttpStatus.CONFLICT.value());
            response.setMessage("Email is already registered");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        user.setName(user.getName().trim());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.CITIZEN);

        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("User registered successfully");
        response.setData(userDao.addUser(user));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseStructure<List<User>>> getAllUsers() {
        ResponseStructure<List<User>> response = new ResponseStructure<>();
        List<User> users = userDao.getAllUsers();

        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage(users.isEmpty() ? "No users found" : "Users fetched successfully");
        response.setData(users);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<User>> getUserById(Integer id) {
        ResponseStructure<User> response = new ResponseStructure<>();
        Optional<User> optionalUser = userDao.getUserById(id);

        if (optionalUser.isEmpty()) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User fetched successfully");
        response.setData(optionalUser.get());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    public ResponseEntity<ResponseStructure<User>> updateUser(User user) {
        ResponseStructure<User> response = new ResponseStructure<>();

        if (user.getId() <= 0) {
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("A valid user ID is required");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = userDao.getUserById(user.getId());

        if (optionalUser.isEmpty()) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        User existingUser = optionalUser.get();

        if (user.getName() != null && !user.getName().isBlank()) {
            existingUser.setName(user.getName().trim());
        }

        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            String email = user.getEmail().trim().toLowerCase();

            if (!email.equals(existingUser.getEmail()) && userDao.existsByEmail(email)) {
                response.setStatusCode(HttpStatus.CONFLICT.value());
                response.setMessage("Email is already registered");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            existingUser.setEmail(email);
        }

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User updated successfully");
        response.setData(userDao.updateUser(existingUser));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<String>> deleteUser(Integer id) {
        ResponseStructure<String> response = new ResponseStructure<>();
        Optional<User> optionalUser = userDao.getUserById(id);

        if (optionalUser.isEmpty()) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        userDao.deleteUser(optionalUser.get());

        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User deleted successfully");
        response.setData("Deleted user ID: " + id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    public ResponseEntity<ResponseStructure<String>> loginUser(User user) {
        ResponseStructure<String> response = new ResponseStructure<>();

        if (user.getEmail() == null || user.getEmail().isBlank()
                || user.getPassword() == null || user.getPassword().isBlank()) {

            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Email and password are required");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        String email = user.getEmail().trim().toLowerCase();
        Optional<User> optionalUser = userDao.findByEmail(email);

        if (optionalUser.isEmpty()
                || !passwordEncoder.matches(user.getPassword(), optionalUser.get().getPassword())) {

            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Invalid email or password");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtil.generateToken(email);

        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Login successful");
        response.setData(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}