package com.homework.homework_week2.user;

import com.homework.homework_week2.exception.errorCode.CustomErrorCode;
import com.homework.homework_week2.exception.exception.RestApiException;
import com.homework.homework_week2.user.domain.User;
import com.homework.homework_week2.user.dto.RegisterRequestDto;
import com.homework.homework_week2.user.dto.UserResponseDto;
import com.homework.homework_week2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;

    /**
     * 회원가입
     * @param requestDto
     * @return
     */
    @PostMapping("/register")
    public boolean register(
            @AuthenticationPrincipal User userDetails,
            @ModelAttribute @Valid RegisterRequestDto requestDto) {

        if (userDetails != null) {
            throw new IllegalArgumentException(CustomErrorCode.ERROR_LOGIN_NECESSARY.getMessage());
        }

        userService.register(requestDto);
        return true;
    }

    /**
     * 로그인
     * @param email
     * @param password
     * @return
     */
    @PostMapping("/login")
    public String login(
            @AuthenticationPrincipal User userDetails,
            @RequestParam String email,
            @RequestParam String password
    ) {
        if (userDetails != null) {
            throw new IllegalArgumentException(CustomErrorCode.ERROR_LOGIN_NECESSARY.getMessage());
        }

        return userService.login(email, password);
    }

    /**
     * 유저 정보 조회
     * @param userDetails
     * @return
     */
    @GetMapping("/user")
    public UserResponseDto getUserInfo(@AuthenticationPrincipal User userDetails) {
        return userService.getUser(userDetails);
    }

}
