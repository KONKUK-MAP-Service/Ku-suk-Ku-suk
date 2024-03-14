package com.cona.KUsukKusuk.spot.controller;

import com.cona.KUsukKusuk.global.response.HttpResponse;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.dto.SpotGetResponse;
import com.cona.KUsukKusuk.spot.dto.SpotUploadRequest;
import com.cona.KUsukKusuk.spot.dto.SpotJoinResponse;
import com.cona.KUsukKusuk.spot.service.SpotService;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("spot")
@RequiredArgsConstructor
public class SpotController {
    final private SpotService spotService;
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "장소 등록", description = "로그인한 사용자의 장소를 등록합니다.")
    public HttpResponse<SpotJoinResponse> saveSpot(SpotUploadRequest spotUploadRequest) throws IOException {

        Spot savedSpot = spotService.uploadSpot(spotUploadRequest);
        return HttpResponse.okBuild(
                SpotJoinResponse.of(savedSpot)
        );
    }

    @GetMapping("/all")
    public HttpResponse<List<SpotGetResponse>> getAllSpots() throws IOException {
        List<SpotGetResponse> spots = spotService.getAllSpots();
        return HttpResponse.okBuild(spots);

    }

}



