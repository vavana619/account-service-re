package com.msa.account.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.account.dto.AccountDto;
import com.msa.account.entity.Account;
import com.msa.account.error.ErrorCode;
import com.msa.account.error.ErrorExceptionController;
import com.msa.account.model.Address;
import com.msa.account.model.Name;
import com.msa.account.service.LoginService;

@SpringBootTest
public class LoginControllerTest {
	
	private Logger logger = LoggerFactory.getLogger(LoginControllerTest.class);

	@InjectMocks
    private LoginController loginController;

    @Mock
    private LoginService loginService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController)
        		.setControllerAdvice(new ErrorExceptionController())
        		.build();
    }
    
    @Test
    @DisplayName("로그인 성공")
    public void login() throws Exception {
        //given
        final AccountDto.LoginReq dto = buildLoginReq();
        Account account = buildAccount();
        given(loginService.login(any(AccountDto.LoginReq.class))).willReturn(account);

        //when
        final ResultActions resultActions = requestLogin(dto);
        
        logger.info(resultActions.toString());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address.address1", is(account.getAddress().getAddress1())))
                .andExpect(jsonPath("$.address.zip", is(account.getAddress().getZip())))
                .andExpect(jsonPath("$.email", is(account.getEmail())))
                .andExpect(jsonPath("$.name.first", is(account.getName().getFirst())))
                .andExpect(jsonPath("$.name.last", is(account.getName().getLast())));
    }
    
    @Test
    @DisplayName("로그인 실패 - Email 형식이 아닌 경우")
    public void login_not_email_format() throws Exception {
        //given
        final AccountDto.LoginReq dto = buildLoginReqNotEamilFormat();
        Account account = buildAccount();
        given(loginService.login(any(AccountDto.LoginReq.class))).willReturn(account);

        //when
        final ResultActions resultActions = requestLogin(dto);
        
        logger.info(resultActions.toString());

        //then
        resultActions
        	.andExpect(status().isBadRequest())
        	.andExpect(jsonPath("$.code", is(ErrorCode.INPUT_VALUE_INVALID.getCode())))
        	.andExpect(jsonPath("$.status", is(ErrorCode.INPUT_VALUE_INVALID.getStatus())))
        	.andExpect(jsonPath("$.message", is(ErrorCode.INPUT_VALUE_INVALID.getMessage())))
        	.andExpect(jsonPath("$.errors[0].field", is("email")))
        	.andExpect(jsonPath("$.errors[0].value", is(dto.getEmail())));
    }
    
    @Test
    @DisplayName("로그인 실패 - Password가 null 인 경우")
    public void login_null_password() throws Exception {
        //given
        final AccountDto.LoginReq dto = buildLoginReqNullPassword();
        Account account = buildAccount();
        given(loginService.login(any(AccountDto.LoginReq.class))).willReturn(account);

        //when
        final ResultActions resultActions = requestLogin(dto);
        
        logger.info(resultActions.toString());

        //then
        resultActions
        	.andExpect(status().isBadRequest())
        	.andExpect(jsonPath("$.code", is(ErrorCode.INPUT_VALUE_INVALID.getCode())))
        	.andExpect(jsonPath("$.status", is(ErrorCode.INPUT_VALUE_INVALID.getStatus())))
        	.andExpect(jsonPath("$.message", is(ErrorCode.INPUT_VALUE_INVALID.getMessage())))
        	.andExpect(jsonPath("$.errors[0].field", is("password")))
        	.andExpect(jsonPath("$.errors[0].value", is(dto.getPassword())));
    }
    
    @Test
    @DisplayName("로그인 실패 - Password가 empty 인 경우")
    public void login_empty_password() throws Exception {
        //given
        final AccountDto.LoginReq dto = buildLoginReqEmptyPassword();
        Account account = buildAccount();
        given(loginService.login(any(AccountDto.LoginReq.class))).willReturn(account);

        //when
        final ResultActions resultActions = requestLogin(dto);
        
        logger.info(resultActions.toString());

        //then
        resultActions
        	.andExpect(status().isBadRequest())
        	.andExpect(jsonPath("$.code", is(ErrorCode.INPUT_VALUE_INVALID.getCode())))
        	.andExpect(jsonPath("$.status", is(ErrorCode.INPUT_VALUE_INVALID.getStatus())))
        	.andExpect(jsonPath("$.message", is(ErrorCode.INPUT_VALUE_INVALID.getMessage())))
        	.andExpect(jsonPath("$.errors[0].field", is("password")))
        	.andExpect(jsonPath("$.errors[0].value", is(dto.getPassword())));
    }
    
    @Test
    @DisplayName("로그인 실패 - Password가 blank 인 경우")
    public void login_blank_password() throws Exception {
        //given
        final AccountDto.LoginReq dto = buildLoginReqBlankPassword();
        Account account = buildAccount();
        given(loginService.login(any(AccountDto.LoginReq.class))).willReturn(account);

        //when
        final ResultActions resultActions = requestLogin(dto);
        
        logger.info(resultActions.toString());

        //then
        resultActions
        	.andExpect(status().isBadRequest())
        	.andExpect(jsonPath("$.code", is(ErrorCode.INPUT_VALUE_INVALID.getCode())))
        	.andExpect(jsonPath("$.status", is(ErrorCode.INPUT_VALUE_INVALID.getStatus())))
        	.andExpect(jsonPath("$.message", is(ErrorCode.INPUT_VALUE_INVALID.getMessage())))
        	.andExpect(jsonPath("$.errors[0].field", is("password")))
        	.andExpect(jsonPath("$.errors[0].value", is(dto.getPassword())));
    }

    private ResultActions requestLogin(AccountDto.LoginReq dto) throws Exception {
        return mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());
    }
    
    private AccountDto.LoginReq buildLoginReq() {
        return AccountDto.LoginReq.builder()
        		.email("tiger@korea.com")
        		.password("lion")
        		.build();
   
    }
    
    private AccountDto.LoginReq buildLoginReqNotEamilFormat() {
        return AccountDto.LoginReq.builder()
        		.email("tiger.korea.com")
        		.password("lion")
        		.build();
   
    }
    
    private AccountDto.LoginReq buildLoginReqNullPassword() {
        return AccountDto.LoginReq.builder()
        		.email("tiger@korea.com")
        		.password(null)
        		.build();
   
    }
    
    private AccountDto.LoginReq buildLoginReqEmptyPassword() {
        return AccountDto.LoginReq.builder()
        		.email("tiger@korea.com")
        		.password("")
        		.build();
   
    }
    
    private AccountDto.LoginReq buildLoginReqBlankPassword() {
        return AccountDto.LoginReq.builder()
        		.email("tiger@korea.com")
        		.password("   ")
        		.build();
   
    }
    
    private Account buildAccount() {
    	return Account.builder()
    			.name(Name.builder().first("호랑이").last("tiger").build())
    			.email("tiger@korea.com")
    			.address(Address.builder()
                		.address1("주소1")
                		.zip("12345")
                		.build())
    			.build();
    }

}
