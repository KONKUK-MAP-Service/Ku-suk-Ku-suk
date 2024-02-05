package com.cona.KUsukKusuk.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@JsonInclude(Include.NON_NULL)
public class HttpResponse<T> extends ResponseEntity<ApiResponse<T>> {
    //Todo:컨트롤러 하드코딩으로 Status값 생성이아니라 매서드로 보내기
    public HttpResponse(HttpStatusCode status) {
        super(status);
    }
    public HttpResponse(HttpStatusCode status, T reuslts) {
        super(new ApiResponse<>(status.value(), reuslts), status);
    }
}
