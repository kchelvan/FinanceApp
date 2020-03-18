package helpers;

import java.util.Random;

public class Account {

    protected String accountType = "";
    protected String accountName = "";
    protected int accountNumber = 0;
    protected double currentBalance = 0.0;
    protected double investmentGoal = 0.0;
    protected double growthRate = 0.0;
    protected double timeToMaturation = 0.0;

    //Default Constructor
    public Account() {
    }

    public Account(String aT, String aN, double cB, double iG, int aNum){
        accountType = aT;
        accountName = aN;
        currentBalance = cB;
        investmentGoal = iG;
        accountNumber = aNum;
        growthRate = generateRate();
    }

    public void addAccount(String accountType, String accountName) {
        this.accountType = accountType;
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountName() {
        return accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getInvestmentGoal() {
        return investmentGoal;
    }
    public void setInvestmentGoal(double investmentGoal) {
        this.investmentGoal = investmentGoal;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }
    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public double getGrowthRate() {
        return growthRate;
    }
    public void setGrowthRate(double growthRate) {
        System.out.println(growthRate);
        this.growthRate = growthRate;
    }

    public int getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void deposit(double amount) {this.currentBalance += amount;}
    public void withdraw(double amount) {this.currentBalance -= amount;}

    private static double generateRate(){
        double start = 1.1;
        double end = 2.4;

        double random = new Random().nextDouble();

        double result = start + (random * (end - start));
        return (double) Math.round(result * 100) / 100;
    }

}
