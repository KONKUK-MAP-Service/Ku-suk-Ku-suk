package com.cona.KUsukKusuk.user.dto;

import com.cona.KUsukKusuk.user.domain.User;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UpdateUserProfileRequest(
        String userid,

         String password,
         String nickname,
         String email
) {

}
