package com.cona.KUsukKusuk.spot.dto;

import com.cona.KUsukKusuk.spot.domain.Spot;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record SpotUploadRequest(
    @NotNull(message = "장소명은 필수 입력값입니다.")
    @Schema(description = "장소명", nullable = false, example = "건대 중문 !")
    String spotName,
    @NotNull(message = "경도 값은 필수 입력값입니다.")
    @Schema(description = "경도 값", nullable = false, example = "33.450701")
    String longitude,

    @Schema(description = "사진, api 요청시 'Content-Type': 'multipart/form-data' 으로 요청을 보내야 합니다. ", example = "'Content-Type': 'multipart/form-data'")
    List<MultipartFile> Images,

    @NotNull(message = "위도 값은 필수 입력값입니다.")
    @Schema(description = "위도 값", nullable = false, example = "126.570667")
    String latitude,
    @NotNull(message = "리뷰(설명) 필수 입력값입니다.")
    @Schema(description = "리뷰(설명)", nullable = false, example = "건대 길냥이가 너무 많아용 ㅎㅎㅎ")
    String review
)
{
    public Spot toEntity(List<String> images) {
        return Spot.builder()
                .spotName(spotName)
                .longitude(longitude)
                .latitude(latitude)
                .review(review)
                .imageUrls(images)
                .build();
    }

}
