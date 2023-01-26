package com.msa.account.exception;

import lombok.Getter;

@Getter
public class DuplicatedEmailException extends RuntimeException {

	private String email;
	private String field;
	
	public DuplicatedEmailException(String email) {
		this.email = email;
		this.field = "email";
	}
}
