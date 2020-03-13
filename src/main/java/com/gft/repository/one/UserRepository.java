package com.gft.repository.one;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gft.bean.User;


@Repository
public interface UserRepository extends JpaRepository<User , Long>{
	
	List<User> findAll();
	
}
