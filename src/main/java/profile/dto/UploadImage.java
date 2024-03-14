package profile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record UploadImage(
        @Schema(description = "프로필 이미지 변경 요청시에만 'Content-Type': 'multipart/form-data' 으로 요청을 보내야 합니다. ", nullable = false, example = "'Content-Type': 'multipart/form-data'")
        MultipartFile profileImage
) {
}
