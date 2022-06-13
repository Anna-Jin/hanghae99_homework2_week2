package com.homework.homework_week2.user;

import com.homework.homework_week2.user.domain.User;
import com.homework.homework_week2.user.dto.RegisterRequestDto;
import com.homework.homework_week2.user.dto.UserResponseDto;
import com.homework.homework_week2.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;

    @Autowired
    private UserRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원가입
     * @param requestDto
     * @return
     */
    @PostMapping("/register")
    public boolean register(@ModelAttribute @Valid RegisterRequestDto requestDto) {
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
            @RequestParam String email,
            @RequestParam String password
    ) {

        return userService.login(email, password);
    }

    @GetMapping("/user")
    public UserResponseDto getUserInfo(@AuthenticationPrincipal User userDetails) {
        return userService.getUser(userDetails.getId());
    }

}
