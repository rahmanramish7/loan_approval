package com.loan.loan_api.repository;

import com.loan.loan_api.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findByUserId(Long userId);
}
