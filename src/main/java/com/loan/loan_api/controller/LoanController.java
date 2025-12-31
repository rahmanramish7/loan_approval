package com.loan.loan_api.controller;

import com.loan.loan_api.dto.LoanApplyRequest;
import com.loan.loan_api.dto.LoanResponse;
import com.loan.loan_api.entity.LoanApplication;
import com.loan.loan_api.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/loans")
public class LoanController {
 @Autowired
 private LoanService loanService;
	
 /**
  * USER applies for a loan
  */
	
 @PostMapping
 public LoanResponse applyLoan(@RequestBody LoanApplyRequest request)
{
	 return loanService.applyLoan(request);
}
 
 /**
  * USER views own loans
  */
 @GetMapping("/my")
 public List<LoanApplication>getMyLoans(){
	  return loanService.getMyLoans();
 }
 
 /**
  * AGENT moves loan from APPLIED → UNDER_REVIEW
  */
 @PutMapping("/{loanId}/review")
 public String reviewLoan(@PathVariable Long loanId) {
     loanService.reviewLoan(loanId);
     return "Loan moved to UNDER_REVIEW";
 }
 
 /**
  * AGENT approves loan: UNDER_REVIEW → APPROVED
  */
 @PutMapping("/{loanId}/approve")
 public String approveLoan(@PathVariable Long loanId) {
	 loanService.approveLoan(loanId);
	 return "Loan APPROVED successfully";
 }
 
 
 
 
 /**
  * AGENT rejects loan: UNDER_REVIEW → REJECTED
  */
 
@PutMapping("/{loanId}/reject")
public String rejection(@PathVariable Long loanId , @RequestBody String reason) {
	 loanService.rejectLoan(loanId,reason);
	 return "Loan REJECTED successfully";
 }
 
	 /**
  * AGENT disburses loan: APPROVED → DISBURSED
  */
 @PutMapping("/{loanId}/disburse")
 public String disburseLoan(@PathVariable Long loanId) {
	 loanService.disburseLoan(loanId);
	 return "Loan DISBURSED successfully";
 }
}



 
 
 
 