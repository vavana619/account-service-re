package com.msa.account.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.msa.account.dto.AccountDto;
import com.msa.account.service.AccountService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("accounts")
@Slf4j
public class AccountController {
	
	@Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<String> signUp(@RequestBody @Valid final AccountDto.SignUpReq dto) {
    	log.info("account-service sign up call");;
    	return accountService.create(dto);
    }

    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public AccountDto.Res getAccount(@PathVariable final String email) {
        return new AccountDto.Res(accountService.findByEmail(email));
    }

    @RequestMapping(value = "/{email}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public AccountDto.Res updateMyAccount(@PathVariable final String email, @RequestBody final AccountDto.UpdateAccountReq dto) {
        return new AccountDto.Res(accountService.updateAccount(email, dto));
    }
    
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<AccountDto.Res> getAccounts() {
    	return accountService.getAccounts().stream()
    			.map(m -> new AccountDto.Res(m))
    			.collect(Collectors.toList());
    }
    
    @DeleteMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> deleteAccounts(@RequestBody @Valid final List<AccountDto.DelReq> dtoList) {
    	accountService.deleteAccounts(dtoList);
    	return ResponseEntity.ok(true);
    }
    
}
