package com.cona.KUsukKusuk.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    public LocalDateTime createdDate = LocalDateTime.now(ZoneId.of("Asia/Seoul")); // 한국 시간으로 생성 날짜 설정

    @LastModifiedDate
    public LocalDateTime updatedDate = LocalDateTime.now(ZoneId.of("Asia/Seoul")); // 한국 시간으로 수정 날짜 설정
}

