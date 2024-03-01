package com.cona.KUsukKusuk.controller;

import com.cona.KUsukKusuk.service.SpotService;
import com.cona.KUsukKusuk.spot.domain.Spot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor //final이 붙은 클래스만 생성자를 만들어줌
public class SpotController {
    private final SpotService spotService;

    @GetMapping("/save")
    public String save(){
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Spot spot) throws IOException {//게시글에서 작성한 데이터를 컨트롤러에 가져오기
        System.out.println("boardDTO = " + spotService);
        spotService.save(spot);
        return "index"; //화면을 띄우는게 아니라 get 매핑을 호출(아래)
    }
}
