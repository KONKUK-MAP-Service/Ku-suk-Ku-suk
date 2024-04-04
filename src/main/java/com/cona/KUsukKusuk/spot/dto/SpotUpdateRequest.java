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

     String spotName,



     String review,

     int deleteImageindex
){

}
