	package com.example.demo.Controller;
	
	import java.util.List;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.security.access.prepost.PreAuthorize;
	import org.springframework.web.bind.annotation.DeleteMapping;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;
	
	import com.example.demo.Model.User;
	import com.example.demo.Service.UserService;
	
	@RestController
	@RequestMapping("/users")
	public class UserController {
	
		@Autowired
		UserService Us;
		
		
		@GetMapping
		@PreAuthorize("hasRole('ADMIN')")
		public List<User> getAllUsers() {
			
			return Us.getAllUsers();
			
		}
		
		@GetMapping("/{id}")
	    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	    public User getUsersbyId(@PathVariable Long id) {
	        return Us.getUserById(id);
	    }
		
		
		 @DeleteMapping("/{id}")
		    @PreAuthorize("hasRole('ADMIN')")
		    public void deleteUsers(@PathVariable Long id) {
		        Us.deleteUser(id);
		    }


		 
		 
		 
		 
		
	}
