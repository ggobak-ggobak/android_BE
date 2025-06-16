package kr.co.msa.android_be.controller;

import jakarta.validation.Valid;
import kr.co.msa.android_be.config.provider.JwtTokenProvider;
import kr.co.msa.android_be.domain.dto.request.SignInRequestDto;
import kr.co.msa.android_be.domain.dto.request.SignUpRequestDto;
import kr.co.msa.android_be.domain.dto.response.SignInResponse;
import kr.co.msa.android_be.domain.dto.response.UserResponse;
import kr.co.msa.android_be.exception.UserException;
import kr.co.msa.android_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        UserResponse response = userService.signUp(signUpRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signin(@Valid @RequestBody SignInRequestDto signInRequestDto) {
        SignInResponse response = userService.signIn(signInRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/signout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        // 토큰 검증
        if (!jwtTokenProvider.validateToken(token.replace("Bearer ", ""))) {
            throw new UserException("유효하지 않은 토큰입니다");
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyInfo(@RequestHeader("Authorization") String token) {
        // 토큰에서 이메일 추출
        String email = jwtTokenProvider.getEmailFromToken(token.replace("Bearer ", ""));

        // 사용자 정보 조회
        UserResponse userInfo = userService.getCurrentUser(email);

        return ResponseEntity.ok(userInfo);
    }
}
