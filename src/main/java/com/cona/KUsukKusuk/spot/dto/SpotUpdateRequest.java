package com.cona.KUsukKusuk.spot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record SpotUpdateRequest (
     Long id,

     String spotName,

     List<String> images,

    @Schema(description = "사진, api 요청시 'Content-Type': 'multipart/form-data' 으로 요청을 보내야 합니다. ", example = "'Content-Type': 'multipart/form-data'")
     List<MultipartFile> Images,


     String review
){

}
