package com.homework.homework_week2.user.service;

import com.homework.homework_week2.common.S3UploadManager;
import com.homework.homework_week2.config.security.jwt.JwtTokenProvider;
import com.homework.homework_week2.user.domain.User;
import com.homework.homework_week2.user.dto.RegisterRequestDto;
import com.homework.homework_week2.user.dto.UserResponseDto;
import com.homework.homework_week2.user.repository.UserRepository;
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

        // 회원 등록
        userRepository.save(User.builder()
                .name(requestDto.getName())
                .nickname(requestDto.getNickname())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword())) // 비밀번호 암호화
                .introduce(requestDto.getIntroduce())
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        return true;
    }

    /**
     * 로그인
     * @param email
     * @param password
     * @return
     */
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("이메일을 확인해주세요."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("패스워드를 확인해주세요.");
        }

        return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
    }

    /**
     * 유저 정보 조회
     * @param userDetails
     * @return
     */
    public UserResponseDto getUser(User userDetails) {
        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        UserResponseDto userInfo = UserResponseDto.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .introduce(user.getIntroduce())
                .createdAt(user.getCreatedAt())
                .build();

        return userInfo;
    }
}
