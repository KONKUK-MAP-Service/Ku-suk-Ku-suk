package com.cona.KUsukKusuk.bookmark.exception;

import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class BookmarkException extends RuntimeException {
    private final HttpStatus httpStatus;

    public BookmarkException(HttpExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.httpStatus = exceptionCode.getHttpStatus();
    }

    public BookmarkException() {
        this(HttpExceptionCode.BOOK_MARK_ERR);
    }

}
