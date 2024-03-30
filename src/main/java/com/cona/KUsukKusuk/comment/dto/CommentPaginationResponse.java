package com.cona.KUsukKusuk.comment.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CommentPaginationResponse (
        /*
        totalComments : 댓글 전체 개수
        curPageNum : 현재 페이지 번호
        lastPage : 마지막 페이지 번호
        amountInBlock : 한 페이지당 보여지는 댓글 개수
        list : 해당 페이지에 들어간 댓글 정보들
         */
        Long totalComments, Long curPageNum, Long lastPage, Long amountInBlock, List<CommentGetResponse> list
){
    public static CommentPaginationResponse of(List<CommentGetResponse> commentsByUser,Long totalComments, Long curPageNum, Long lastPage, Long amountInBlock)
    {

        return CommentPaginationResponse.builder()
                .totalComments(totalComments)
                .curPageNum(curPageNum)
                .lastPage(lastPage)
                .amountInBlock(amountInBlock)
                .list(commentsByUser)
                .build();
    }
}
