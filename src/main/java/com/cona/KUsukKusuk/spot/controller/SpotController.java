package com.cona.KUsukKusuk.spot.controller;

import com.cona.KUsukKusuk.global.response.HttpResponse;
import com.cona.KUsukKusuk.picture.Picture;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.dto.SpotJoinRequest;
import com.cona.KUsukKusuk.spot.dto.SpotJoinResponse;
import com.cona.KUsukKusuk.spot.service.SpotService;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.UserJoinResponse;
import com.cona.KUsukKusuk.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spot")
public class SpotController {
    final private SpotService spotService;

    public SpotController(SpotService spotService) {
        this.spotService = spotService;
    }

    @PostMapping("/register")
    @Operation(summary = "장소 등록", description = "로그인 한 경우, 장소 등록")
    public HttpResponse<SpotJoinResponse> saveSpot(@RequestBody SpotJoinRequest spotJoinRequest){//JoinRequest 쓰임

        Spot spot = spotJoinRequest.toEntity(spotService);
        Spot savedSpot = spotService.save(spot);


        return  HttpResponse.okBuild(
                SpotJoinResponse.of(savedSpot)
        );
    }
}



