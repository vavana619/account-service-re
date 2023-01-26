package com.msa.account.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.msa.account.entity.Account;
import com.msa.account.entity.Role;
import com.msa.account.model.Address;
import com.msa.account.model.Name;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

public class AccountDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUpReq {
    	
    	@Email
        private String email;
    	
    	@Valid
    	private Name name;
    	
    	@NotBlank
        private String password;
    	
    	@Valid
    	private Address address;
    	
    	private Role role;

        @Builder
        public SignUpReq(String email, Name name, String password, Address address, String zip, Role role) {
            this.email = email;
            this.name = name;
            this.password = password;
            this.address = address;
            this.role = role;
        }

        public Account toEntity() {
            return Account.builder()
                    .email(this.email)
                    .name(this.name)
                    .password(this.password)
                    .address(this.address)
                    .role(this.role)
                    .build();
        }
        public Account toEntity(Role role) {
            return Account.builder()
                    .email(this.email)
                    .name(this.name)
                    .password(this.password)
                    .address(this.address)
                    .role(role)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateAccountReq {
    	
        @Valid
    	private Address address;

        @Builder
        public UpdateAccountReq(Address address, String zip) {
            this.address = address;
        }
    }
    
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginReq {
    	
    	@Email
    	@NonNull
        private String email;
    	
    	@NotBlank
        private String password;

        @Builder
        public LoginReq(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @Getter
    public static class Res {
    	private Long accountId;
        private String email;
        private Name name;
        private Address address;
        private Role role;

        public Res(Account account) {
        	this.accountId = account.getId();
            this.email = account.getEmail();
            this.name = account.getName();
            this.address = account.getAddress();
            this.role = account.getRole();
        }
    }
    
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DelReq {
    	private Long accountId;
    	
    	@Builder
    	public DelReq(long accountId) {
    		this.accountId = accountId;
    		
    	}
    	
    }
}
