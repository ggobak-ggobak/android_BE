package kr.co.msa.android_be.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 사용자 정보 응답
@Getter
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;

    @Builder
    public UserResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
