package clientside.helpers;

// This is a helper class to calculate investment values
// FV = IV * (1 + rate)^years
public class Investment{
    private double initialInvestment;
    private double investmentGoal;
    private double growthRate;
    private double years;

    // Investment goal calculator
    public void investmentFV(double initialInvestment, double growthRate, double years){
        this.initialInvestment = initialInvestment;
        this.growthRate = growthRate;
        this.years = years;

        this.investmentGoal = initialInvestment * Math.pow((1 + growthRate), years);
    }
    
    // Investment initial value calculator
    public void investmentIV(double investmentGoal, double growthRate, double years){
        this.investmentGoal = investmentGoal;
        this.growthRate = growthRate;
        this.years = years;

        this.initialInvestment = investmentGoal / Math.pow(1 + growthRate,years);
    }

    // Investment Growth Rate Calculator
    public void investmentGR(double initialInvestment, double investmentGoal, double years){
        this.initialInvestment = initialInvestment;
        this.investmentGoal = investmentGoal;
        this.years = years;

        this.growthRate = Math.pow(investmentGoal/initialInvestment, 1 / years) - 1;

    }

    // Investment Years calculator
    public void investmentYears(double initialInvestment, double investmentGoal, double growthRate){
        this.initialInvestment = initialInvestment;
        this.investmentGoal = investmentGoal;
        this.growthRate = growthRate;

        this.years = Math.log(investmentGoal/initialInvestment)/Math.log(1+growthRate);
    }

    // Accessors for the class
    public double getInitialInvestment(){ return this.initialInvestment;}
    public double getInvestmentGoal(){ return this.investmentGoal;}
    public double getGrowthRate(){ return this.growthRate;}
    public double getYears(){ return this.years;}
}