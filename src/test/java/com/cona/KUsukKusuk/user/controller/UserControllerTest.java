package com.cona.KUsukKusuk.user.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cona.KUsukKusuk.support.RestDocsTest;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.UserJoinRequest;
import com.cona.KUsukKusuk.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.swing.Spring;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.session.MockitoSessionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;


@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@Slf4j
public class UserControllerTest extends RestDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("회원가입시 필수항목 null시 예외를 던진다")
    void join_exception_Test() throws Exception {
        //given
        UserJoinRequest userJoinRequest
                = new UserJoinRequest(null, "vdongv1620", "email", "최동훈");

        //when
        MvcResult perform = mockMvc.perform(post("/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userJoinRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

        //then
        Exception resolvedException = perform.getResolvedException();
        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);

//      assertThat(resolvedException.getMessage()).isEqualTo("사용자 아이디는 필수 입력 값입니다.");

    }

    @Test
    @DisplayName("회원가입 성공시 User의 정보를 정해진 형식으로 반환한다.")
    void join() throws Exception {
        //given
        UserJoinRequest userJoinRequest
                = new UserJoinRequest("최동훈", "vdongv1620", "email", "최동훈");

        User mockUser = User.builder()
                .id(1L)
                .userId("최동훈")
                .password("vdongv1620")
                .email("email")
                .nickname("최동훈")
                .build();

        BDDMockito.given(userService.save(userJoinRequest)).willReturn(mockUser);

        //when
        ResultActions perform = mockMvc.perform(post("/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(userJoinRequest)));

        //반환값 확인
        MvcResult mvcResult = perform.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.results.id").value(1L))
                .andExpect(jsonPath("$.results.userid").value("최동훈"))
                .andExpect(jsonPath("$.results.password").value("vdongv1620"))
                .andExpect(jsonPath("$.results.email").value("email"))
                .andExpect(jsonPath("$.results.nickname").value("최동훈"));


    }
}
