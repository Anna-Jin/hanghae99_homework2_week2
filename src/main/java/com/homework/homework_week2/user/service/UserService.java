package com.homework.homework_week2.user.service;

import com.homework.homework_week2.common.FileManagerService;
import com.homework.homework_week2.config.security.JwtTokenProvider;
import com.homework.homework_week2.user.domain.User;
import com.homework.homework_week2.user.dto.RegisterRequestDto;
import com.homework.homework_week2.user.repoistory.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final FileManagerService fileManagerService;

    private final UserRepository userRepository;

    /**
     * 회원가입
     * @param requestDto
     * @return
     */
    public boolean register(RegisterRequestDto requestDto) {
        // 이메일 중복 검사
        Optional<User> found = userRepository.findByEmail(requestDto.getEmail());
        if (found.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호에 닉네임과 같은 값이 포함되어있는 지 검사
        if (requestDto.getPassword().contains(requestDto.getNickname())) {
            throw new IllegalArgumentException("비밀번호에 닉네임이 포함될 수 없습니다.");
        }

        // 회원 등록
        userRepository.save(User.builder()
                .name(requestDto.getName())
                .nickname(requestDto.getNickname())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword())) // 비밀번호 암호화
                .introduce(requestDto.getIntroduce())
                .profileImageUrl(
                    fileManagerService.savaFile(requestDto.getEmail(), requestDto.getFile()) // 파일 저장
                )
                .roles(Collections.singletonList("ROLE_USER"))
                .build()
        );

        return true;
    }


    public String login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("잘못된 이메일입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
    }
}