package com.swm.smartwaste.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swm.smartwaste.Entity.User;
import com.swm.smartwaste.Services.UserServices;
import com.swm.smartwaste.dto.ResponseStructure;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    @PostMapping
    public ResponseEntity<ResponseStructure<User>> addUser(@RequestBody User user) {
        return userServices.addUser(user);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseStructure<List<User>>> getAllUsers() {
        return userServices.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<User>> getUserById(@PathVariable Integer id) {
        return userServices.getUserById(id);
    }
    
    @PutMapping
    public ResponseEntity<ResponseStructure<User>> updateUser(@RequestBody User user) {
        return userServices.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteUser(@PathVariable Integer id) {
        return userServices.deleteUser(id);
    }
    
    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<String>> loginUser(@RequestBody User user) {
        return userServices.loginUser(user);
    }
}