package kr.co.msa.android_be.service;

import kr.co.msa.android_be.domain.dto.request.ExpenseRequestDto;
import kr.co.msa.android_be.domain.dto.response.ExpenseResponseDto;
import kr.co.msa.android_be.domain.entity.Expense;
import kr.co.msa.android_be.domain.entity.User;
import kr.co.msa.android_be.exception.UserException;
import kr.co.msa.android_be.repository.ExpenseRepository;
import kr.co.msa.android_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    @Transactional
    public ExpenseResponseDto createExpense(Long userId, ExpenseRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("사용자를 찾을 수 없습니다"));

        Expense expense = Expense.builder()
                .user(user)
                .date(request.getDate())
                .category(request.getCategory())
                .amount(request.getAmount())
                .memo(request.getMemo())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();

        Expense savedExpense = expenseRepository.save(expense);

        return ExpenseResponseDto.builder()
                .expenseId(savedExpense.getExpenseId())
                .userId(savedExpense.getUser().getUserId())
                .date(savedExpense.getDate())
                .category(savedExpense.getCategory())
                .amount(savedExpense.getAmount())
                .memo(savedExpense.getMemo())
                .latitude(savedExpense.getLatitude())
                .longitude(savedExpense.getLongitude())
                .createdAt(savedExpense.getCreatedAt())
                .build();
    }

    public List<ExpenseResponseDto> getExpenses(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("사용자를 찾을 수 없습니다"));

        return expenseRepository.findByUserOrderByDateDesc(user).stream()
                .map(expense -> ExpenseResponseDto.builder()
                        .expenseId(expense.getExpenseId())
                        .userId(expense.getUser().getUserId())
                        .date(expense.getDate())
                        .category(expense.getCategory())
                        .amount(expense.getAmount())
                        .memo(expense.getMemo())
                        .latitude(expense.getLatitude())
                        .longitude(expense.getLongitude())
                        .createdAt(expense.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ExpenseResponseDto> getExpensesByDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("사용자를 찾을 수 없습니다"));

        return expenseRepository.findByUserAndDateBetweenOrderByDateDesc(user, startDate, endDate).stream()
                .map(expense -> ExpenseResponseDto.builder()
                        .expenseId(expense.getExpenseId())
                        .userId(expense.getUser().getUserId())
                        .date(expense.getDate())
                        .category(expense.getCategory())
                        .amount(expense.getAmount())
                        .memo(expense.getMemo())
                        .latitude(expense.getLatitude())
                        .longitude(expense.getLongitude())
                        .createdAt(expense.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}