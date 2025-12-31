package com.loan.loan_api.util;

public class LoanCalculator {

    public static double calculateEmi(double amount, double rate, int months) {
        double monthlyRate = rate / 12 / 100;
        return (amount * monthlyRate * Math.pow(1 + monthlyRate, months)) /
               (Math.pow(1 + monthlyRate, months) - 1);
    }
}
