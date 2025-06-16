package kr.co.msa.android_be.service;

import kr.co.msa.android_be.domain.dto.request.SignInRequestDto;
import kr.co.msa.android_be.domain.dto.request.SignUpRequestDto;
import kr.co.msa.android_be.domain.dto.response.SignInResponse;
import kr.co.msa.android_be.domain.dto.response.UserResponse;

public interface UserService {
    UserResponse signUp(SignUpRequestDto requestDto);
    SignInResponse signIn(SignInRequestDto requestDto);
    void signOut();
    UserResponse getCurrentUser(String email);
}
