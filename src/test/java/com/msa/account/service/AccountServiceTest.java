package com.msa.account.service;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import com.msa.account.dto.AccountDto;
import com.msa.account.entity.Account;
import com.msa.account.exception.AccountNotFoundException;
import com.msa.account.model.Address;
import com.msa.account.model.Name;
import com.msa.account.repository.AccountRepository;

@SpringBootTest
public class AccountServiceTest {
	
	@InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;


//    @Test
//    @DisplayName("회원가입 성공")
//    public void create_회원가입_성공() {
//        //given
//        final AccountDto.SignUpReq dto = buildSignUpReq();
//        given(accountRepository.save(any(Account.class))).willReturn(dto.toEntity());
//
//        //when
//        final Account account = accountService.create(dto);
//
//        //then
//        verify(accountRepository, atLeastOnce()).save(any(Account.class));
//        assertThatEqual(dto, account);
//    }
    
    @Test
    @DisplayName("회원가입 실패 - DuplicateKeyException")
    public void create_회원가입_실패_DuplicateKeyException() {
        //given
        final AccountDto.SignUpReq dto = buildSignUpReqFail();
        given(accountRepository.findByEmail(anyString())).willReturn(new ArrayList<>());

        assertThrows(DuplicateKeyException.class, () -> {
        	accountService.create(dto);
        });
    }
    
//    @Test
//    @DisplayName("계정조회 - 존재하는 경우")
//    public void findById_존재하는경우_계정리턴() {
//        //given
//        final AccountDto.SignUpReq dto = buildSignUpReq();
//        given(accountRepository.findById(anyLong())).willReturn(dto.toEntity());
//
//        //when
//        final Account account = accountService.findById(anyString());
//
//        //then
//        assertThatEqual(dto, account);
//    }
//    
//    @Test
//    @DisplayName("계정조회 - 존재 하지 않은 경우")
//    public void findById_존재하지않은경우_AccountNotFoundException() {
//        //given
//        given(accountRepository.getById(anyString())).willReturn(null);
//
//        assertThrows(AccountNotFoundException.class, () -> {
//        	 accountService.findById(anyString());
//        });
//    }
//    
//    @Test
//    @DisplayName("계정수정 성공")
//    public void updateAccount() {
//        //given
//        final AccountDto.SignUpReq signUpReq = buildSignUpReq();
//        final AccountDto.UpdateAccountReq dto = buildUpdateAccountReq();
//        given(accountRepository.getById(anyString())).willReturn(signUpReq.toEntity());
//
//        //when
//        final Account account = accountService.updateAccount(anyString(), dto);
//
//        //then
//        assertThat(dto.getAddress().getAddress1(), is(account.getAddress().getAddress1()));
//        assertThat(dto.getAddress().getZip(), is(account.getAddress().getZip()));
//    }
    
    private AccountDto.SignUpReq buildSignUpReq() {
        return AccountDto.SignUpReq.builder()
        		.address(Address.builder()
                		.address1("프랑스")
                		.zip("12345")
                		.build())
                .email("dictionary@france.com")
                .name(Name.builder().first("나폴레옹").last("napol").build())
                .password("short")
                .build();
    }
    
    private AccountDto.SignUpReq buildSignUpReqFail() {
        return AccountDto.SignUpReq.builder()
    			.name(Name.builder().first("호랑이").last("tiger").build())
    			.email("tiger@korea.com")
    			.password("lion")
    			.address(Address.builder()
                		.address1("주소1")
                		.zip("12345")
                		.build())
    			.build();
    }
    
    private AccountDto.UpdateAccountReq buildUpdateAccountReq() {
        return AccountDto.UpdateAccountReq.builder()
        		.address(Address.builder()
        				.address1("코리아")
        				.zip("98765")
        				.build())
                .build();
    }
    
    private void assertThatEqual(AccountDto.SignUpReq signUpReq, Account account) {
        assertThat(signUpReq.getAddress().getAddress1(), is(account.getAddress().getAddress1()));
        assertThat(signUpReq.getAddress().getZip(), is(account.getAddress().getZip()));
        assertThat(signUpReq.getEmail(), is(account.getEmail()));
//        assertThat(signUpReq.getName(), is(account.getName()));
        assertThat(signUpReq.getName().getFirst(), is(account.getName().getFirst()));
        assertThat(signUpReq.getName().getLast(), is(account.getName().getLast()));
    }
}
