package com.kosta.moyoung.advice;

import org.springframework.util.Assert;
import  com.kosta.moyoung.advice.error.DefaultAuthenticationException;

public class DefaultAssert extends Assert {
	
	public static void isAuthentication(String message){
        throw new DefaultAuthenticationException(message);
    }
	public static void isAuthentication(boolean value){
        if(!value){
            throw new DefaultAuthenticationException(ErrorCode.INVALID_AUTHENTICATION);
        }
    }
}
