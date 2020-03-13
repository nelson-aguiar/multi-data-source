package com.gft.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.bean.User;
import com.gft.repository.one.UserRepository;
import com.gft.repository.two.UserRepositoryTwo;

@RequestMapping("/student")
@RestController
public class StudentResource {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	UserRepositoryTwo userRepoTwo;

	@GetMapping
	public ResponseEntity<List<User>> getStudent() {
		return ResponseEntity.status(200).body(userRepository.findAll());
	}
	
	@GetMapping("/two")
	public ResponseEntity<List<User>> getStudentTwo() {
		User u = new User();
		u.setId("15946");
		u.setName("Nerso Teste insert second domain");
		userRepoTwo.save(u);
		return ResponseEntity.status(200).body(userRepoTwo.findAll());
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<User> saveStudent(@RequestBody User student) {		
		System.out.println(student);
		User u = new User();
		u.setId(student.getId());
		u.setName(student.getName());
		userRepoTwo.save(u);
		return ResponseEntity.status(200).body(userRepository.save(student));
	}
	
	@GetMapping("/load-data")
	public ResponseEntity<String> loadData(){
		User student = new User();
		student.setId("1");
		student.setName("Mcclain");
		
		userRepository.save(student);
		
		student = new User();
		student.setId("2");
		student.setName("Dostoievski");
		
		userRepository.save(student);		
		
		return ResponseEntity.ok("Student inserted with succefully");
	}
	

}
