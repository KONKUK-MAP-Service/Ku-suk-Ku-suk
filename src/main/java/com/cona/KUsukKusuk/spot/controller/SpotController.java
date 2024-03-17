package com.cona.KUsukKusuk.spot.controller;

import com.cona.KUsukKusuk.global.response.HttpResponse;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.dto.SpotDeleteResponse;
import com.cona.KUsukKusuk.spot.dto.SpotDetailResponse;
import com.cona.KUsukKusuk.spot.dto.SpotGetResponse;
import com.cona.KUsukKusuk.spot.dto.SpotUpdatResponse;
import com.cona.KUsukKusuk.spot.dto.SpotUpdateRequest;
import com.cona.KUsukKusuk.spot.dto.SpotUploadRequest;
import com.cona.KUsukKusuk.spot.dto.SpotJoinResponse;
import com.cona.KUsukKusuk.spot.service.SpotService;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("spot")
@Tag(name = "장소(마커) 컨트롤러", description = "장소(마커) 도메인에 대한 컨트롤러 입니다.")

@RequiredArgsConstructor
public class SpotController {
    final private SpotService spotService;
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "장소(마커) 등록", description = "로그인한 사용자의 장소를 등록합니다.")
    public HttpResponse<SpotJoinResponse> saveSpot(@RequestPart(value = "multipartFileList", required = false) List<MultipartFile> multipartFileList,
                                                   @RequestPart(value = "posting") SpotUploadRequest spotUploadRequest) throws IOException {

        Spot savedSpot = spotService.uploadSpot(multipartFileList, spotUploadRequest);
        return HttpResponse.okBuild(
                SpotJoinResponse.of(savedSpot)
        );
    }
    @GetMapping("/all")
    @Operation(summary = "비로그인 사용자 장소(마커) 전체조회", description = "로그인 하지 않은 사용자의 화면에 보이는 모든 장소를 조회합니다. ")

    public HttpResponse<List<SpotGetResponse>> getPublicAllSpots() throws IOException {
        List<SpotGetResponse> spots = spotService.getAllPublicSpots();
        return HttpResponse.okBuild(spots);

    }

    @GetMapping("/login-all")
    @Operation(summary = "로그인 사용자 장소(마커) 전체조회", description = "로그인한 사용자의 화면에 보이는 모든 장소를 조회합니다. ")

    public HttpResponse<List<SpotGetResponse>> getAllSpots() throws IOException {
        List<SpotGetResponse> spots = spotService.getAllSpots();
        return HttpResponse.okBuild(spots);

    }
    @GetMapping("/{spotId}")
    @Operation(summary = "장소 상세 조회", description = "로그인 유무에 상관없이 장소를 상세 조회합니다. ")

    public HttpResponse<SpotDetailResponse> getSpotDetails(@PathVariable Long spotId) {
        SpotDetailResponse spotDetails = spotService.getSpotDetails(spotId);
        return HttpResponse.okBuild(spotDetails);
    }

    @PutMapping("/{spotId}")
    @Operation(summary = "장소 수정", description = "로그인한 사용자가 등록한 장소를 수정합니다. 만약 본인의 장소가 아니면  400 애러가 발생합니다.")

    public HttpResponse<SpotUpdatResponse> updateSpot(@PathVariable Long spotId, @RequestPart (value= "multipartFileList", required = false) List<MultipartFile> multipartFileList, @RequestPart(value="update") SpotUpdateRequest spotUpdateRequest)
            throws IOException {
        Spot updatedSpot = spotService.updateSpot(spotId, multipartFileList, spotUpdateRequest);
        return HttpResponse.okBuild(
                SpotUpdatResponse.of(updatedSpot)
        );
    }
    @DeleteMapping("/{spotId}")
    @Operation(summary = "장소 삭제", description = "로그인한 사용자가 등록한 장소를 삭제합니다. 만약 본인의 장소가 아니면  400 애러가 발생합니다.")

    public HttpResponse<SpotDeleteResponse> deleteSpot(@PathVariable Long spotId) {
        spotService.deleteSpot(spotId);
        return HttpResponse.okBuild(
                SpotDeleteResponse.of("장소삭제가 성공적으로 이루어졌습니다.")
        );
    }

}



