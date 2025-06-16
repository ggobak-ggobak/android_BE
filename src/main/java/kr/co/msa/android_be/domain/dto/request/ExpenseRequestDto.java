package kr.co.msa.android_be.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ExpenseRequestDto {
    @NotNull(message = "날짜는 필수 입력값입니다")
    private LocalDate date;

    @NotBlank(message = "카테고리는 필수 입력값입니다")
    private String category;

    @NotNull(message = "금액은 필수 입력값입니다")
    @Positive(message = "금액은 0보다 커야 합니다")
    private BigDecimal amount;

    private String memo;

    @NotNull(message = "위도는 필수 입력값입니다")
    private Double latitude;

    @NotNull(message = "경도는 필수 입력값입니다")
    private Double longitude;
}