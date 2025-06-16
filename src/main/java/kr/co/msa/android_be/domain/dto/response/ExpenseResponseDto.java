package kr.co.msa.android_be.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponseDto {
    private Long expenseId;
    private Long userId;
    private LocalDate date;
    private String category;
    private BigDecimal amount;
    private String memo;
    private Double latitude;
    private Double longitude;
    private LocalDateTime createdAt;
}