package com.tsarit.form_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsarit.form_1.model.userData;

@Repository
public interface userRepository extends JpaRepository<userData, Integer>{
  
	public userData findByemailid(String em);
}
