package com.practice.authorization.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private ResponseStatus responseStatus;
    private String errorMsg;
}
