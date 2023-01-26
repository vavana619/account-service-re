package com.msa.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msa.account.entity.Account;

public interface LoginRepository  extends JpaRepository<Account, Long> {

	List<Account> findByEmail(String email);
//	Optional<Account> findByEmail(String email);
	
}
