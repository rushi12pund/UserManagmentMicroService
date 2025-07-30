
package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.User;

public interface UserService {

	    List<User> getAllUsers();
	    User getUserById(Long id);
	    void deleteUser(Long id);
}

