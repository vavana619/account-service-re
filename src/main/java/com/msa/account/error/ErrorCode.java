package com.msa.account.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
	
	INPUT_VALUE_INVALID("ERR_COM_001", "입력값이 올바르지 않습니다.", 400),
	ACCOUNT_NOT_FOUND("ERR_ACC_001", "해당 회원을 찾을 수 없습니다.", 404),
    EMAIL_DUPLICATION("ERR_ACC_002", "이메일이 중복되었습니다.", 400),
    WRONG_PASSWORD("ERR_ACC_003", "패스워드가 맞지 않습니다.", 403);
	
	
	private final String code;
	private final String message;
	private final int status;
	
	ErrorCode(String code, String message, int status) {
		this.code = code;
		this.message = message;
		this.status = status;
	}
}
