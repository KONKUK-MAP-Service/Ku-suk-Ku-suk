package com.cona.KUsukKusuk.spot.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long commentId;
    private String content;
    private String author;
    boolean deletable;
    private LocalDateTime createdDate;
    private String profileImage;

}
