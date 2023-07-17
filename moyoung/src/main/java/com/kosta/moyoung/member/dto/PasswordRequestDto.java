package com.kosta.moyoung.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequestDto {
	
	  private String email;
	  private String newPassword;
}
