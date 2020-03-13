package com.nelsonaguiar.repository.two;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nelsonaguiar.bean.User;


@Repository
public interface UserRepositoryTwo extends JpaRepository<User , Long>{
	
	List<User> findAll();
	
}
