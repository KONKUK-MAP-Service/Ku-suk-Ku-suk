package com.cona.KUsukKusuk.spot.controller;

import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.service.SpotService;
import com.cona.KUsukKusuk.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("spot")
public class SpotController {
    final private SpotService spotService;

    public SpotController(SpotService spotService) {
        this.spotService = spotService;
    }

    @PostMapping("/make")
    @Operation(summary = "장소 등록", description = "로그인 안 한 경우, 장소 등록")
    public ResponseEntity<Spot> saveSpot(@ModelAttribute Spot spot){
        Spot savedSpot = spotService.save(spot);
        return  ResponseEntity.ok()
                .body(spot);
    }
}



