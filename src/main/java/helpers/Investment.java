package main.java.helpers;

// This is a helper class to calculate investment values
// Didn't do monthly because it makes growth rate and year math harder
// FV = IV * (1 + rate)^years
public class Investment{
    private double initialInvestment;
    private double investmentGoal;
    private double growthRate;
    private int years;

    // Investment goal calculator
    public void investmentFV(double initialInvestment, double growthRate, int years){
        this.initialInvestment = initialInvestment;
        this.growthRate *= growthRate;
        this.years *= years;

        //this.investmentGoal = initialInvestment * Math.pow((1+growthRate/1200),(double)years*12);
        this.investmentGoal = initialInvestment * Math.pow((1 + growthRate),(double) years);
    }
    
    // Investment initial value calculator
    public void investmentIV(double investmentGoal, double growthRate, int years){
        this.investmentGoal = investmentGoal;
        this.growthRate = growthRate;
        this.years = years;

        //this.initialInvestment = investmentGoal / Math.pow((1+growthRate/1200),(double)years*12);
        this.initialInvestment = investmentGoal / Math.pow(1 + growthRate,(double)years);
    }

    // Investment Growth Rate Calculator
    public void investmentGR(double initialInvestment, double investmentGoal, int years){
        this.initialInvestment = initialInvestment;
        this.investmentGoal = investmentGoal;
        this.years = years;

        //this.growthRate = (Math.pow(initialInvestment / investmentGoal, 1 / (double)years * 12) - 1) * 1200;
        this.growthRate = Math.pow(investmentGoal/initialInvestment, 1 / (double) years) - 1;

    }

    // Investment Years calculator
    public void investmentYears(double initialInvestment, double investmentGoal, double growthRate){
        this.initialInvestment = initialInvestment;
        this.investmentGoal = investmentGoal;
        this.growthRate = growthRate;

        this.years = (int) Math.ceil(Math.log(investmentGoal/initialInvestment)/Math.log(1+growthRate));
    }

    // Accessors for the class
    public double getInitialInvestment(){ return this.initialInvestment;}
    public double getInvestmentGoal(){ return this.investmentGoal;}
    public double getGrowthRate(){ return this.growthRate;}
    public int getYears(){ return this.years;}
}