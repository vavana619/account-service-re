package com.msa.account.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.msa.account.dto.AccountDto;
import com.msa.account.service.LoginService;

@RestController
@RequestMapping("login")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public AccountDto.Res login(@RequestBody @Valid final AccountDto.LoginReq dto) {
        return new AccountDto.Res(loginService.login(dto));
    }
}
