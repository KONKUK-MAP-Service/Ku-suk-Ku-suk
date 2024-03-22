package com.cona.KUsukKusuk.comment.dto;

import lombok.Builder;

@Builder
public record CommentUpdateRequest (
    String comment
){}
