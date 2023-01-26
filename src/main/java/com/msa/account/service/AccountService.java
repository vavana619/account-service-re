package com.msa.account.service;


import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.msa.account.dto.AccountDto;
import com.msa.account.entity.Account;
import com.msa.account.entity.Role;
import com.msa.account.exception.AccountNotFoundException;
import com.msa.account.repository.AccountRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
@Transactional
@Slf4j
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private WebClient webClient;

	public Mono<String> create(AccountDto.SignUpReq dto) {
		if(!accountRepository.findByEmail(dto.getEmail()).isEmpty()) {
			throw new DuplicateKeyException(dto.getEmail());			
		}
		
		Account account = accountRepository.save(dto.toEntity(Role.ROLE_USER));
		
		return this.sendAccountDataToReserve(account.getId(), account.getEmail());
	}

	@Transactional(readOnly = true)
	public Account findByEmail(String email) {
	    List<Account> accounts = accountRepository.findByEmail(email);
	    if (accounts.isEmpty())
	        throw new AccountNotFoundException(email);
	    return accounts.get(0);
	}
	
	public Account updateAccount(String email, AccountDto.UpdateAccountReq dto) {
	    Account account = findByEmail(email);
	    account.updateAccount(dto);
	    return account;
	}
	
	public List<Account> getAccounts() {
		return accountRepository.findAll();
	}
	
	public void deleteAccounts(List<AccountDto.DelReq> dtoList) {
		accountRepository.deleteAllById((Iterable<? extends Long>) dtoList.stream().map(m -> m.getAccountId()).collect(Collectors.toList()));
	}
	
	@SuppressWarnings("unchecked")
	private Mono<String> sendAccountDataToReserve(long id, String email) {
		JSONObject jsonReq = new JSONObject();
		jsonReq.put("id", id);
		jsonReq.put("email", email);;
		
		return this.webClient.post()
				.uri("/account")
				.accept(MediaType.APPLICATION_JSON)
				.bodyValue(jsonReq)
				.retrieve()
				.bodyToMono(String.class);
	}
	
}
