package com.loan.loan_api.dto;

import com.loan.loan_api.enums.LoanStatus;

public class LoanResponse {

    private Long loanId;
    private Double amount;
    private Double emi;
    private LoanStatus status;
    private String message;

    public Long getLoanId() { return loanId; }
    public void setLoanId(Long loanId) { this.loanId = loanId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public Double getEmi() { return emi; }
    public void setEmi(Double emi) { this.emi = emi; }

    public LoanStatus getStatus() { return status; }
    public void setStatus(LoanStatus status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
