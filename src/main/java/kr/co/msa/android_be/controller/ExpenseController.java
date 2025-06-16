package kr.co.msa.android_be.controller;

import jakarta.validation.Valid;
import kr.co.msa.android_be.config.provider.JwtTokenProvider;
import kr.co.msa.android_be.domain.dto.request.ExpenseRequestDto;
import kr.co.msa.android_be.domain.dto.response.ExpenseResponseDto;
import kr.co.msa.android_be.domain.entity.User;
import kr.co.msa.android_be.exception.UserException;
import kr.co.msa.android_be.repository.UserRepository;
import kr.co.msa.android_be.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<ExpenseResponseDto> createExpense(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody ExpenseRequestDto request) {
        String email = jwtTokenProvider.getEmailFromToken(token.replace("Bearer ", ""));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("사용자를 찾을 수 없습니다"));

        ExpenseResponseDto response = expenseService.createExpense(user.getUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> getExpenses(
            @RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmailFromToken(token.replace("Bearer ", ""));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("사용자를 찾을 수 없습니다"));

        List<ExpenseResponseDto> expenses = expenseService.getExpenses(user.getUserId());
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/range")
    public ResponseEntity<List<ExpenseResponseDto>> getExpensesByDateRange(
            @RequestHeader("Authorization") String token,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        String email = jwtTokenProvider.getEmailFromToken(token.replace("Bearer ", ""));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("사용자를 찾을 수 없습니다"));

        List<ExpenseResponseDto> expenses = expenseService.getExpensesByDateRange(
                user.getUserId(), startDate, endDate);
        return ResponseEntity.ok(expenses);
    }
}