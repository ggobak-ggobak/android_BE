package kr.co.msa.android_be.service;

import kr.co.msa.android_be.config.provider.JwtTokenProvider;
import kr.co.msa.android_be.domain.dto.request.SignInRequestDto;
import kr.co.msa.android_be.domain.dto.request.SignUpRequestDto;
import kr.co.msa.android_be.domain.dto.response.SignInResponse;
import kr.co.msa.android_be.domain.dto.response.UserResponse;
import kr.co.msa.android_be.domain.entity.User;
import kr.co.msa.android_be.exception.UserException;
import kr.co.msa.android_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserResponse signUp(SignUpRequestDto signUpRequestDto) {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new UserException("중복됨.");
        }

        // 비밀번호 해잌
        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

        // 유저 생성
        User user = User.builder()
                .email(signUpRequestDto.getEmail())
                .username(signUpRequestDto.getName())
                .password(encodedPassword)
                .build();

        User saveUser = userRepository.save(user);

        return UserResponse.builder()
                .id(saveUser.getUserId())
                .email(saveUser.getEmail())
                .name(saveUser.getUsername())
                .build();
    }

    @Override
    public SignInResponse signIn(SignInRequestDto request) {
        // 사용자 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException("이메일 또는 비밀번호가 일치하지 않습니다"));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserException("이메일 또는 비밀번호가 일치하지 않습니다");
        }

        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(user.getEmail());

        // 응답 생성
        return SignInResponse.builder()
                .token(token)
                .user(UserResponse.builder()
                        .id(user.getUserId())
                        .email(user.getEmail())
                        .name(user.getUsername())
                        .build())
                .build();
    }

    @Override
    public void signOut() {

    }

    @Override
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("사용자를 찾을 수 없습니다"));

        return UserResponse.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .name(user.getUsername())
                .build();
    }
}
