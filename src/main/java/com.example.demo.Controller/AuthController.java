package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dao.AuthDao;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthDao As;
	
	@PostMapping("/register")
	public void register(@RequestBody RegisterRequest req) {
		
		As.register(req);
	}
	
	@PostMapping("/login")
	public AuthResponse login(@RequestBody AuthRequest req) {
		return As.authenticate(req);
	}
	

	
	
	
	
}
