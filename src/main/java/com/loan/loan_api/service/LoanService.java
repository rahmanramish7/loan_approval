package com.loan.loan_api.service;

import com.loan.loan_api.dto.LoanApplyRequest;
import com.loan.loan_api.dto.LoanResponse;
import com.loan.loan_api.entity.LoanApplication;
import com.loan.loan_api.entity.User;
import com.loan.loan_api.enums.LoanStatus;
import com.loan.loan_api.repository.LoanRepository;
import com.loan.loan_api.repository.UserRepository;
import com.loan.loan_api.util.LoanCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.loan.loan_api.exception.LoanNotFoundException;
import com.loan.loan_api.exception.InvalidLoanStateException;
import com.loan.loan_api.security.SecurityUtil;


import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepo;

    @Autowired
    private UserRepository userRepo;

    /**
     * TEMPORARY method
     * Later this will come from SecurityContext (JWT)
     */
    private User getCurrentUser() {

        String username = SecurityUtil.getCurrentUsername();

        return userRepo.findByUsername(username)
                .orElseThrow(() ->
                    new RuntimeException("User not found with username " + username));
    }

    // ================= APPLY LOAN =================

    public LoanResponse applyLoan(LoanApplyRequest request) {

        User user = getCurrentUser();

        // RULE 1: Credit score check
        if (request.getCreditScore() < 600) {
            return rejectInstantly("Credit score too low");
        }

        // RULE 2: Max loan amount = 20 × monthly income
        double maxLoan = request.getMonthlyIncome() * 20;
        if (request.getAmount() > maxLoan) {
            return rejectInstantly("Loan amount exceeds allowed limit");
        }

        // EMI calculation
        double interestRate = 10.5;
        double emi = LoanCalculator.calculateEmi(
                request.getAmount(),
                interestRate,
                request.getTenureMonths()
        );

        // RULE 3: EMI should not exceed 40% of income
        if (emi > request.getMonthlyIncome() * 0.40) {
            return rejectInstantly("EMI exceeds 40% of monthly income");
        }

        // Create loan
        LoanApplication loan = new LoanApplication();
        loan.setUser(user);
        loan.setAmount(request.getAmount());
        loan.setTenureMonths(request.getTenureMonths());
        loan.setMonthlyIncome(request.getMonthlyIncome());
        loan.setCreditScore(request.getCreditScore());
        loan.setInterestRate(interestRate);
        loan.setEmi(emi);
        loan.setStatus(LoanStatus.APPLIED);

        loanRepo.save(loan);

        LoanResponse response = new LoanResponse();
        response.setLoanId(loan.getId());
        response.setAmount(loan.getAmount());
        response.setEmi(loan.getEmi());
        response.setStatus(loan.getStatus());
        response.setMessage("Loan applied successfully");

        return response;
    }

    // ================= VIEW OWN LOANS =================

    public List<LoanApplication> getMyLoans() {
        User user = getCurrentUser();
        return loanRepo.findByUserId(user.getId());
    }

    // ================= REVIEW LOAN (AGENT) =================

    public void reviewLoan(Long loanId) {

        LoanApplication loan = getLoanOrThrow(loanId);

        // VALID STATE CHECK
        if (loan.getStatus() != LoanStatus.APPLIED) {
        	  throw new InvalidLoanStateException(
        		        "Loan must be under Applied status to be reviewed"
        		    );
        }

        loan.setStatus(LoanStatus.UNDER_REVIEW);
        loanRepo.save(loan);
    }

    // ================= APPROVE LOAN (ADMIN) =================

    public void approveLoan(Long loanId) {

        LoanApplication loan = getLoanOrThrow(loanId);

        if (loan.getStatus() != LoanStatus.UNDER_REVIEW) {
        	  throw new InvalidLoanStateException(
        		        "Loan must be under review"
        		    );        }

        loan.setStatus(LoanStatus.APPROVED);
        loanRepo.save(loan);
    }

    // ================= REJECT LOAN (ADMIN) =================

    public void rejectLoan(Long loanId, String reason) {

        LoanApplication loan = getLoanOrThrow(loanId);

        if (loan.getStatus() != LoanStatus.UNDER_REVIEW) {
        	  throw new InvalidLoanStateException(
        		        "Loan must be under review"
        		    );        }

        loan.setStatus(LoanStatus.REJECTED);
        loan.setRejectionReason(reason);
        loanRepo.save(loan);
    }

    // ================= DISBURSE LOAN (ADMIN) =================

    public void disburseLoan(Long loanId) {

        LoanApplication loan = getLoanOrThrow(loanId);

        if (loan.getStatus() != LoanStatus.APPROVED) {
        	  throw new InvalidLoanStateException(
        		        "Loan must be under approved status to be disbursed"
        		    );        }

        loan.setStatus(LoanStatus.DISBURSED);
        loanRepo.save(loan);
    }

    // ================= COMMON METHODS =================

    private LoanApplication getLoanOrThrow(Long loanId) {
        return loanRepo.findById(loanId)
                .orElseThrow(() ->
                    new LoanNotFoundException("Loan with id " + loanId + " not found"));
    }


    private LoanResponse rejectInstantly(String reason) {
        LoanResponse response = new LoanResponse();
        response.setStatus(LoanStatus.REJECTED);
        response.setMessage(reason);
        return response;
    }
}




//
//“I extract the authenticated username 
//from SecurityContextHolder and load the actual 
//user from the database, removing any hardcoded user logic.”