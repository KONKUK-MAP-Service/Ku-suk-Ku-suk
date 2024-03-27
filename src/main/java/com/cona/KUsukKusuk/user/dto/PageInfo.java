package com.cona.KUsukKusuk.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
