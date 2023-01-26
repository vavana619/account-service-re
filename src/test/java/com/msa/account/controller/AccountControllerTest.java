package com.msa.account.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.account.dto.AccountDto;
import com.msa.account.entity.Account;
import com.msa.account.model.Address;
import com.msa.account.model.Name;
import com.msa.account.service.AccountService;

@SpringBootTest
public class AccountControllerTest {

	@InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

//    @Test
//    @DisplayName("회원가입")
//    public void signUp() throws Exception {
//        //given
//        final AccountDto.SignUpReq dto = buildSignUpReq();
//        given(accountService.create(any())).willReturn(dto.toEntity());
//
//        //when
//        final ResultActions resultActions = requestSignUp(dto);
//
//        //then
//        resultActions
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.address.address1", is(dto.getAddress().getAddress1())))
//                .andExpect(jsonPath("$.address.zip", is(dto.getAddress().getZip())))
//                .andExpect(jsonPath("$.email", is(dto.getEmail())))
//                .andExpect(jsonPath("$.name.first", is(dto.getName().getFirst())))
//                .andExpect(jsonPath("$.name.last", is(dto.getName().getLast())));
//    }
    
    @Test
    @DisplayName("계정조회")
    public void getAccount() throws Exception {
        //given
        final AccountDto.SignUpReq dto = buildSignUpReq();
        given(accountService.findByEmail(anyString())).willReturn(dto.toEntity());

        //when
        final ResultActions resultActions = requestGetAccount();

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address.address1", is(dto.getAddress().getAddress1())))
                .andExpect(jsonPath("$.address.zip", is(dto.getAddress().getZip())))
                .andExpect(jsonPath("$.email", is(dto.getEmail())))
                .andExpect(jsonPath("$.name.first", is(dto.getName().getFirst())))
                .andExpect(jsonPath("$.name.last", is(dto.getName().getLast())));
    }
    
    @Test
    @DisplayName("계정수정")
    public void updateAccount() throws Exception {
        //given
        final AccountDto.UpdateAccountReq dto = buildUpdateAccountReq();
        final Account account = Account.builder()
                .address(Address.builder()
                		.address1(dto.getAddress().getAddress1())
                		.zip(dto.getAddress().getZip())
                		.build())
                .build();

        given(accountService.updateAccount(anyString(), any(AccountDto.UpdateAccountReq.class))).willReturn(account);

        //when
        final ResultActions resultActions = requestUpdateAccount(dto);

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address.address1", is(dto.getAddress().getAddress1())))
                .andExpect(jsonPath("$.address.zip", is(dto.getAddress().getZip())));

    }
    
    private ResultActions requestSignUp(AccountDto.SignUpReq dto) throws Exception {
        return mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());
    }
    
    private ResultActions requestGetAccount() throws Exception {
        return mockMvc.perform(get("/accounts/tiger@korea.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
    
    private ResultActions requestUpdateAccount(AccountDto.UpdateAccountReq dto) throws Exception {
        return mockMvc.perform(put("/accounts/tiger@korea.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());
    }

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
    
    private AccountDto.UpdateAccountReq buildUpdateAccountReq() {
        return AccountDto.UpdateAccountReq.builder()
        		.address(Address.builder()
        				.address1("코리아")
        				.zip("98765")
        				.build())
                .build();
    }


}
