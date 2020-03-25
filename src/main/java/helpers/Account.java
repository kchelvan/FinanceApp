package main.java.helpers;

import java.util.Random;

public class Account {

    // Variable Declaration
    protected String accountType = "";
    protected String accountName = "";
    protected int accountNumber = 0;
    protected double currentBalance = 0.0;
    protected double investmentGoal = 0.0;
    protected double growthRate = 0.0;
    protected double timeToMaturation = 0.0;

    //Default Constructor
    public Account() {
        growthRate = generateRate();
    }

    public Account(String aT, String aN, double cB, double iG, int aNum, double rate){
        accountType = aT;
        accountName = aN;
        currentBalance = cB;
        investmentGoal = iG;
        accountNumber = aNum;
        growthRate = rate;
        setTimeToMaturation();
    }

    // Instantiates an account
    public void addAccount(String accountType, String accountName) {
        this.accountType = accountType;
        this.accountName = accountName;
    }


    // Getters and Setters
    public String getAccountType() {
        return accountType;
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

    public double getGrowthRate() {
        return growthRate;
    }
    public void setGrowthRate(double growthRate) {
        this.growthRate = growthRate;
    }

    public int getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getTimeToMaturation() { return timeToMaturation; }
    public void setTimeToMaturation() {
        timeToMaturation = (double) Math.round(Math.log(investmentGoal/currentBalance)/Math.log(1+growthRate) * 100)/100;
    }

    // Performs Deposits and Withdrawals from account
    public void deposit(double amount) {this.currentBalance += amount;}
    public void withdraw(double amount) {this.currentBalance -= amount;}

    // Assigns a randomly generated double to represent the growth rate (between the range of 1.1 to 2.4)
    private static double generateRate(){
        double start = 1.1;
        double end = 2.4;

        double random = new Random().nextDouble();

        double result = start + (random * (end - start));
        return (double) Math.round(result * 100) / 100;
    }

    // Converts Account class to CSV
	public String toString(){
        String retString = "";
        retString += accountType + ",";
        retString += accountName + ",";
        retString += currentBalance + ",";
        retString += investmentGoal + ",";
        retString += accountNumber + ",";
        retString += Double.toString(growthRate);

        return retString;
	}
}
