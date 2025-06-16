package kr.co.msa.android_be.controller;

import jakarta.validation.Valid;
import kr.co.msa.android_be.domain.dto.request.SignInRequestDto;
import kr.co.msa.android_be.domain.dto.request.SignUpRequestDto;
import kr.co.msa.android_be.domain.dto.response.SignInResponse;
import kr.co.msa.android_be.domain.dto.response.UserResponse;
import kr.co.msa.android_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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
}
