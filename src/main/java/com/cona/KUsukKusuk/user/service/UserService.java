package com.cona.KUsukKusuk.user.service;

import com.cona.KUsukKusuk.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void save() {

    }

}
