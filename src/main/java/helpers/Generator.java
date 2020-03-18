package main.java.helpers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import main.java.helpers.styles.Styling;
import helpers.Account;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Generator {
    // Global Variable Declaration
    Styling styles = new Styling();
    Stage form =  new Stage();
    Integer windowWidth = styles.windowSize().get(0);

    public HBox generateAccount(Account ac){
        // Variable Declaration
        String accountType  = ac.getAccountType();
        String accountName = ac.getAccountName();
        Double currentBalance = ac.getCurrentBalance();
        Double maxBalance = ac.getInvestmentGoal();
        Double investmentRate = ac.getGrowthRate();
        Integer accountNum = ac.getAccountNumber();
        Double growthTime = ac.getTimeToMaturation();

        HBox account = new HBox();
        VBox accountDetails = new VBox();
        Group accountPie = new Group();
        Canvas canvas = new Canvas(400, 350);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Generate Pie Chart depicted Account Details
        accountPie.getChildren().add(canvas);

        double partition = (currentBalance / maxBalance) * 360;
        gc.setFill(Color.web("#A5ACD3", 1.0));
        gc.fillArc(100, 40, 280, 280, 0, 360, ArcType.ROUND);
        gc.setFill(Color.web("#616BA2", 1.0));
        gc.fillArc(100, 40, 280, 280, 0, partition, ArcType.ROUND);

        // Variable Declaration for Account Details
        Label title = new Label(accountType + " Account");
        Label name = new Label("Account Name: " + accountName);
        Label balance = new Label("Balance: " + currentBalance.toString());
        Label investmentGoal = new Label("Investment Goal: " + maxBalance.toString());
        Label growthRate = new Label("Growth Rate: " + investmentRate.toString());
        Label timeToMaturation = new Label("Time until Maturation: " + growthTime.toString() + " years");

        if (accountNum % 2 != 0) { account.setStyle("-fx-background-color: #BEC3D4"); }

        // Font Styling for Account Details
        title.setStyle(styles.titleText());
        name.setStyle(styles.labelText());
        balance.setStyle(styles.labelText());
        investmentGoal.setStyle(styles.labelText());
        growthRate.setStyle(styles.labelText());
        timeToMaturation.setStyle(styles.labelText());

        accountDetails.setPadding(new Insets(75, 150, 0, 250));

        // Generate Table displaying details of Account
        accountDetails.getChildren().addAll(
                title,
                name,
                balance,
                investmentGoal,
                growthRate,
                timeToMaturation
        );

        accountDetails.setSpacing(15);

        // Adds the Pie Chart and Account Details to the HBox
        account.getChildren().addAll(accountPie, accountDetails);

        // Returns HBox containing account details
        return account;
    }
    public VBox generateInvestmentCalculator(ArrayList<Account> accountsList) {
        // Variable Declaration
        VBox vBox = new VBox();
        VBox calculator = new VBox();
        VBox accounts = new VBox();
        VBox details = new VBox();
        HBox header = new HBox();

        TextField initialInvestText = new TextField();
        TextField investmentGoalText = new TextField();
        TextField interestRateText = new TextField();
        TextField yearsText = new TextField();
        Button calculate = new Button();

        Label calculatorTitle = new Label("Investment Calculator");
        Label title = new Label("Investment Growth");
        Label initialInvestLabel = new Label("Initial Investment: " + initialInvestText.getText());
        Label investmentGoalLabel = new Label("Investment Goal: " + investmentGoalText.getText() );
        Label interestRateLabel = new Label("Growth Rate: " + interestRateText.getText());
        Label timeToMaturation = new Label("Time til Maturation: ");

        // Font and Padding Styling for Account Details
        calculatorTitle.setStyle(styles.pageHeadText());
        title.setStyle(styles.titleText());
        initialInvestLabel.setStyle(styles.labelText());
        investmentGoalLabel.setStyle(styles.labelText());
        interestRateLabel.setStyle(styles.labelText());
        timeToMaturation.setStyle(styles.labelText());

        details.setSpacing(15);
        details.setPadding(new Insets(15, 0, 0, 40));
        details.getChildren().addAll(title, initialInvestLabel, investmentGoalLabel, interestRateLabel, timeToMaturation);

        calculator.setSpacing(15);
        calculator.setPadding(new Insets(10, 10, 10, 10));
        calculator.setAlignment(Pos.BOTTOM_RIGHT);

        // Append each element of the calculator to the calculator VBox
        calculator.getChildren().addAll(calculatorTitle, initialInvestText, investmentGoalText, interestRateText, yearsText, calculate);

        // Initializes Placeholder text for the textfields in the Calculator
        initialInvestText.setPromptText("Initial Investment");
        investmentGoalText.setPromptText("Investment Goal");
        interestRateText.setPromptText("Growth Rate");
        yearsText.setPromptText("Set Year(s)");
        calculate.setText("Calculate");

        // Styling for the Elements in the Calculator
        initialInvestText.setStyle("-fx-min-height: 50");
        investmentGoalText.setStyle("-fx-min-height: 50");
        interestRateText.setStyle("-fx-min-height: 50");
        yearsText.setStyle("-fx-min-height: 50");
        calculate.setStyle(styles.confirmButton());

        // If the user provides three fields in the calculator, calculate the value of the missing field
        calculate.setOnMouseClicked(e -> {
            String initialInvestment = "";
            String investmentGoal = "";
            String interestRate = "";
            String time = "";

            if (initialInvestText.getText().isEmpty() &&
                    !investmentGoalText.getText().isEmpty() &&
                    !interestRateText.getText().isEmpty() &&
                    !yearsText.getText().isEmpty()) {
                initialInvestment = "$" + Math.round(Double.parseDouble(investmentGoalText.getText()) /
                        (1 + (Double.parseDouble(interestRateText.getText()) / 100)
                                * Double.parseDouble(yearsText.getText()))*100.0) / 100.0;
            }
            else if (investmentGoalText.getText().isEmpty() &&
                    !initialInvestText.getText().isEmpty() &&
                    !interestRateText.getText().isEmpty() &&
                    !yearsText.getText().isEmpty()) {
                investmentGoal = "$" + Math.round(Double.parseDouble(initialInvestText.getText()) *
                        (1 + ((Double.parseDouble(interestRateText.getText()) / 100) *
                                Double.parseDouble(yearsText.getText())))*100.0 * (100.0/100.0));
            }
            else if (interestRateText.getText().isEmpty() &&
                    !initialInvestText.getText().isEmpty() &&
                    !investmentGoalText.getText().isEmpty() &&
                    !yearsText.getText().isEmpty()) {
                interestRate = Math.round((Double.parseDouble(investmentGoalText.getText()) /
                        Double.parseDouble(initialInvestText.getText())) - 1) /
                        Double.parseDouble(yearsText.getText()) * 100.0 * (100.0/100.0) + "%";
            }
            else if (yearsText.getText().isEmpty() &&
                    !initialInvestText.getText().isEmpty() &&
                    !investmentGoalText.getText().isEmpty() &&
                    !interestRateText.getText().isEmpty()) {
                time = Double.toString(Math.round(
                        ((Double.parseDouble(investmentGoalText.getText()) /
                                Double.parseDouble(initialInvestText.getText())) - 1) /
                                (Double.parseDouble(interestRateText.getText())/100 * 100.0)/100.0)) + " years";
            }

            // Display the update values in the account details VBox
            initialInvestLabel.setText("Initial Investment: " +
                    (initialInvestment != "" ? initialInvestment : initialInvestText.getText().isEmpty() ? "" : "$" + initialInvestText.getText()));
            investmentGoalLabel.setText("Investment Goal: " +
                    (investmentGoal != "" ? investmentGoal : investmentGoalText.getText().isEmpty() ? "" : "$" + investmentGoalText.getText()));
            interestRateLabel.setText("Growth Rate: " +
                    (interestRate != "" ? interestRate : interestRateText.getText().isEmpty() ? "" : interestRateText.getText() + "%"));
            timeToMaturation.setText("Time til Maturation: " +
                    (time != "" ? time : yearsText.getText().isEmpty() ? "" : yearsText.getText() + " years"));
        });

        // Styling for the Account Details VBox
        accounts.setSpacing(15);
        accounts.setAlignment(Pos.TOP_CENTER);
        accounts.setPadding(new Insets(15,0,0,50));

        // Instantiates Objects for the Accounts Details VBox
        Label accountsTitle = new Label("Import Account Details");
        accountsTitle.setFont(new Font("Rockwell", 20));
        accounts.getChildren().add(accountsTitle);

        Integer savingsAccountsCount = 0;
        // Creates a selectable button for each Savings account that the user has
        for (int i = 0; i < accountsList.size(); i++) {
            if (accountsList.get(i).getAccountType() == "Savings" && savingsAccountsCount < 8) {
                Button currAccountSelection = new Button(accountsList.get(i).getAccountName());
                currAccountSelection.setStyle(styles.savingsButton());
                accounts.getChildren().add(currAccountSelection);
                savingsAccountsCount += 1;

                int index = i;
                currAccountSelection.setOnMouseClicked(e -> {
                    initialInvestText.setText(Double.toString(accountsList.get(index).getCurrentBalance()));
                    investmentGoalText.setText(Double.toString(accountsList.get(index).getInvestmentGoal()));
                    interestRateText.setText(Double.toString(accountsList.get(index).getGrowthRate()));
                    yearsText.setText(Double.toString(accountsList.get(index).getTimeToMaturation()));
                });
            }
        }

        // Combines all header items to the main header HBox
        header.getChildren().addAll(calculator, details, accounts);

        // Generates Line Graph Depicting estimated Growth of Investment
        XYChart<Number, Number> lineGraph = generateGraph();
        lineGraph.setPadding(new Insets(50, 0, 0, 0));

        vBox.setStyle("-fx-background-color: #D8DEF1");
        vBox.setPrefHeight(1080.0);
        vBox.getChildren().addAll(header, lineGraph);

        return vBox;
    }

    public HBox generateNavigation() {
        // Variable Declaration
        HBox navigation = new HBox();
        Button myAccountNav = new Button("My Account");
        Button investmentCalculatorNav = new Button("Investment Calculator");

        // Styling for the Navigation Buttons
        myAccountNav.setStyle("-fx-font-size: 15.0");
        investmentCalculatorNav.setStyle("-fx-font-size: 15.0");

        myAccountNav.setMinWidth(windowWidth/2);
        myAccountNav.setMinHeight(35);
        investmentCalculatorNav.setMinWidth(windowWidth/2);
        investmentCalculatorNav.setMinHeight(35);
        myAccountNav.setStyle(styles.navDeselcted());
        investmentCalculatorNav.setStyle(styles.navSelected());

        navigation.getChildren().addAll(myAccountNav, investmentCalculatorNav);

        // Returns an HBox containing the styled navigation buttons
        return navigation;
    }

    public HBox generateFooter() {
        // Variable Declaration
        HBox footer = new HBox();
        Button transfer = new Button("Transfer To Account");
        Button deposit = new Button("Deposit");
        Button withdraw = new Button("Withdraw");
        Button openAccount = new Button("Open Account");

        // Styling for each Footer Button
        deposit.setStyle(styles.footerButton());
        withdraw.setStyle(styles.footerButton());
        openAccount.setStyle(styles.footerButton());
        transfer.setStyle(styles.footerButton());

        deposit.setMinWidth(windowWidth/4);
        withdraw.setMinWidth(windowWidth/4);
        openAccount.setMinWidth(windowWidth/4);
        transfer.setMinWidth(windowWidth/4);

        deposit.setMinHeight(35);
        withdraw.setMinHeight(35);
        openAccount.setMinHeight(35);
        transfer.setMinHeight(35);

        footer.getChildren().addAll(openAccount, deposit, withdraw, transfer);

        // Returns an HBox containing all the footer buttons
        return footer;
    }

    public LineChart<Number, Number> generateGraph() {
        // Variable Declaration
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number,Number> lineChart = new LineChart<>(xAxis,yAxis);

        // Assigns title for the line graph
        lineChart.setTitle("Expected Growth of Initial Investment");
        lineChart.setPrefHeight(650);

        // Assigns data points for the line graph depicting the estimated growth using the user's provided values
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < 10; i++) {
            series.getData().add(new XYChart.Data(i * 1000, i * 1500));
        }

        // Returns a Line Chart Object
        return lineChart;
    }

    public ScrollPane updateList(ArrayList<Account> accountsList, Stage primaryStage, VBox vBox) {
        // Variable Declaration
        ScrollPane accounts = new ScrollPane();
        VBox accountsVBox = new VBox();

        // Updates accounts list with updated values
        for (int i = 0; i < accountsList.size(); i++) {
            accountsVBox.getChildren().add(generateAccount(accountsList.get(i)));
        }

        // Allows the user to scroll through the available accounts
        accounts.setContent(accountsVBox);

        // Styling for the VBox containing the different user accounts
        accountsVBox.setMinHeight(accountsList.size() * 350);

        // Styling for scroll pane
        accounts.setStyle("-fx-background: #D8DEF1; -fx-background-color: #D8DEF1");
        accounts.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        vBox.getChildren().set(2, accounts);

        accountsVBox.setOnMouseClicked(e -> {
            // Variable Declaration
            // Obtains index of selected account
            Integer accountIndex = (int) e.getY() / 350;
            Account account = accountsList.get(accountIndex);

            // Open Form to edit account
            updateAccount(account, primaryStage, accountsList, vBox);
        });
        accountsVBox.setOnMouseMoved(e -> {
            // Variable Declaration
            // Obtains index of selected account
            Integer accountIndex = (int) e.getY() / 350;
            if (primaryStage.isFocused()) {
                if (accountsList.size() > accountIndex) {
                    // Reset's surrounding account rows to their respective colour
                    for (int i = 0; i < accountsList.size(); i++) {
                        if (i != accountIndex) {
                            accountsVBox.getChildren().get(i).setStyle((i % 2 != 0)
                                    ? "-fx-background-color: #BEC3D4"
                                    : "-fx-background-color: #D8DEF1");
                        }
                    }
                    // Updates the background of the current account row to indicate that it is highlighted
                    accountsVBox.getChildren().get(accountIndex).setStyle((accountIndex % 2 != 0)
                            ? "-fx-background-color: #C1B3E2"
                            : "-fx-background-color: #D9CFEB");
                }
            }
        });

        // Return VBox with updated accounts list
        return accounts;
    }

    public void updateAccount(Account account, Stage primaryStage, ArrayList<Account> accountsList, VBox vBox) {
        // Variable Declaration
        GridPane selection = new GridPane();
        selection.setVgap(5);
        selection.setHgap(5);

        Label title = new Label("Edit Account");
        Label accountTypeLabel = new Label("Account Type");
        Label accountNameLabel = new Label("Account Name");
        Label investmentGoalLabel = new Label("Investment Goal");
        Label growthRateLabel = new Label("Growth Rate");

        TextField accountTypeText = new TextField();
        TextField accountNameText = new TextField();
        TextField investmentGoalText = new TextField();
        TextField growthRateText = new TextField();

        Button update = new Button("Update Account");

        // Styling for the Form Elements
        selection.setAlignment(Pos.CENTER);

        title.setStyle(styles.labelTitleform());
        accountTypeLabel.setStyle(styles.labelForm());
        accountNameLabel.setStyle(styles.labelForm());
        investmentGoalLabel.setStyle(styles.labelForm());
        growthRateLabel.setStyle(styles.labelForm());

        accountTypeText.setStyle(styles.selectForm());
        accountNameText.setStyle(styles.selectForm());
        investmentGoalText.setStyle(styles.selectForm());
        growthRateText.setStyle(styles.selectForm());

        update.setStyle(styles.buttonForm());

        // Properties of each Text Field
        accountTypeText.setDisable(true);
        accountTypeText.setText(account.getAccountType());
        accountNameText.setText(account.getAccountName());
        investmentGoalText.setText(Double.toString(account.getInvestmentGoal()));
        growthRateText.setText(Double.toString(account.getGrowthRate()));

        if (account.getAccountType() != "Savings") {
            investmentGoalText.setDisable(true);
            growthRateText.setDisable(true);
        }

        // Adds each form item to the main gridpane
        selection.add(title, 1, 0);
        selection.add(accountTypeLabel, 0, 1);
        selection.add(accountTypeText, 1, 1);
        selection.add(accountNameLabel, 0, 2);
        selection.add(accountNameText, 1, 2);
        selection.add(investmentGoalLabel, 0, 3);
        selection.add(investmentGoalText, 1, 3);
        selection.add(growthRateLabel, 0, 4);
        selection.add(growthRateText, 1, 4);
        selection.add(update, 1, 5);

        // Styling for the main stage
        selection.setStyle("-fx-background-color: #B8BEDD");

        // Updates the account details with the user's entered values
        update.setOnMouseClicked(e ->{
            account.setAccountName(accountNameText.getText());
            account.setInvestmentGoal(Double.parseDouble(investmentGoalText.getText()));
            account.setGrowthRate(Double.parseDouble(growthRateText.getText()));
            updateList(accountsList, primaryStage, vBox);
            // Closes the form once the user submits their changes
            form.close();
        });

        // Displays the Edit Account Form to the user
        form.setScene(new Scene(selection, 450, 450));
        form.show();
    }
}
