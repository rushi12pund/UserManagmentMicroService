package com.example.demo.Dao;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Config.JwtCL;
import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import com.example.demo.Repo.UserRepo;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;

@Service
public class AuthDao {
    
	@Autowired
	UserRepo Us;
	
	@Autowired
	PasswordEncoder Pe;
	
	@Autowired 
	JwtCL Jc;
	
	// User Registration
	
	public void register(RegisterRequest request) {
        if (Us.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(Pe.encode(request.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        Us.save(user);
    }
	
	 // User Authentication (Login)
	public AuthResponse authenticate(AuthRequest request) {
        User user = Us.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!Pe.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String token = Jc.generateToken(user);
        return new AuthResponse(token);
	
	
	
	}
	
}
