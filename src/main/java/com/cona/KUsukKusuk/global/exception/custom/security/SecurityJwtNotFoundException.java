package com.cona.KUsukKusuk.global.exception.custom.security;

import com.cona.KUsukKusuk.global.exception.ApplicationException;
import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;

public class SecurityJwtNotFoundException extends ApplicationException {
    public SecurityJwtNotFoundException(HttpExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public SecurityJwtNotFoundException(HttpExceptionCode exceptionCode, String exceptionMessage) {
        super(exceptionCode, exceptionMessage);
    }
    public SecurityJwtNotFoundException(){
        this(HttpExceptionCode.JWT_NOT_FOUND);}
}
