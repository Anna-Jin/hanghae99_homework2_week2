package com.homework.homework_week2.user;

import com.homework.homework_week2.user.dto.RegisterRequestDto;
import com.homework.homework_week2.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public boolean register(@ModelAttribute RegisterRequestDto requestDto) {
        userService.register(requestDto);

        // 에러처리 할 것.
        return true;
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password
    ) {

        //service 연결
        String jwtToken = userService.login(email, password);

        return jwtToken;
    }

}
