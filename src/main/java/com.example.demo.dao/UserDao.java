package com.example.demo.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.User;
import com.example.demo.Repo.UserRepo;
import com.example.demo.Service.UserService;
import com.example.demo.exception.UserNotFoundException;


@Service
public class UserDao  implements UserService  {

	@Autowired
	UserRepo Us;
	
	@Override
	public List<User> getAllUsers() {
		
		return Us.findAll();
	}

	@Override
	public User getUserById(Long id) {
	
		return Us.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));	}

	@Override
	public void deleteUser(Long id) {
		
		if(!Us.existsById(id)) {
			throw new UserNotFoundException("User not found");
		}
		
		Us.deleteById(id);
		
		
	}

}
