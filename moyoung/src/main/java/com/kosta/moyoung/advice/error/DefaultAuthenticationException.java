package com.kosta.moyoung.advice.error;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;

import com.kosta.moyoung.advice.ErrorCode;


@Getter
public class DefaultAuthenticationException extends AuthenticationException{
	
	@Autowired
    private ErrorCode errorCode;

    public DefaultAuthenticationException(String msg, Throwable t) {
        super(msg, t);
        this.errorCode = ErrorCode.INVALID_REPRESENTATION;
    }

    public DefaultAuthenticationException(String msg) {
        super(msg);
    }

    public DefaultAuthenticationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
