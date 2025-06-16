package kr.co.msa.android_be.repository;

import kr.co.msa.android_be.domain.entity.Expense;
import kr.co.msa.android_be.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserOrderByDateDesc(User user);
    List<Expense> findByUserAndDateBetweenOrderByDateDesc(User user, LocalDate startDate, LocalDate endDate);
    List<Expense> findByUserAndCategoryOrderByDateDesc(User user, String category);
}