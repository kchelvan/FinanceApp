import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import styles.Styling;

public class main extends Application {
    // Global Variable Declaration
    Integer windowWidth = 1024;
    Integer windowHeight = 1080;
    Integer page = 0;
    Styling styles = new Styling();

    // Single form used to allow only one form to be open at a time
    Stage form =  new Stage();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Variable Declaration
        primaryStage.setTitle("Finance Application");
        VBox vBox = new VBox();
        VBox accountsVBox = new VBox();
        HBox navigation = generateNavigation();
        HBox footer = generateFooter();
        ScrollPane accounts = new ScrollPane();

        // Menu Bar Functionality
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu exitFile = new Menu("Exit");

        // Close main program when the exit button is selected from the Menu Bar
        exitFile.setOnAction(e -> {
            menuFile.hide();
            primaryStage.close();
        });

        // Add a File and Exit button to the Menu Bar
        menuFile.getItems().addAll(exitFile);
        menuBar.getMenus().addAll(menuFile);

        // Displays information about each Account open for the user
        for (int i = 0; i < 4; i++) {
            accountsVBox.getChildren().add(generateAccount(
                    "Savings",
                    500.0,
                    1500.0,
                    0.15,
                    i));
        }
        // Styling for the VBox containing the different user accounts
        accountsVBox.setMinHeight(990);

        // Allows the user to scroll through the available accounts
        accounts.setContent(accountsVBox);
        // Styling for scroll pane
        accounts.setStyle("-fx-background: #D8DEF1; -fx-background-color: #D8DEF1");
        accounts.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        vBox.getChildren().addAll(menuBar, navigation, accounts , footer);

        // Creates an instance of the investment calculator
        VBox investmentCalculator = generateInvestmentCalculator();

        // Allows switching between the two navigation tabs
        navigation.getChildren().get(0).setOnMouseClicked(e -> {
            if (page != 0) {
                vBox.getChildren().set(2, accounts);
                vBox.getChildren().add(footer);
                // Styling for the Navigation tab
                ((HBox) vBox.getChildren().get(1)).getChildren().get(0).setStyle("" +
                        "-fx-background-color: #D8DEF1;" +
                        "-fx-background-radius: 0;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: Rockwell");
                ((HBox) vBox.getChildren().get(1)).getChildren().get(1).setStyle("" +
                        "-fx-background-color: #8A93C0;" +
                        "-fx-background-radius: 0;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: Rockwell");
                page = 0;
            }
        });
        navigation.getChildren().get(1).setOnMouseClicked(e -> {
            if (page != 1) {
                vBox.getChildren().set(2, investmentCalculator);
                vBox.getChildren().remove(3);
                // Styling for the Navigation tab
                ((HBox) vBox.getChildren().get(1)).getChildren().get(0).setStyle("" +
                        "-fx-background-color: #8A93C0;" +
                        "-fx-background-radius: 0;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: Rockwell");
                ((HBox) vBox.getChildren().get(1)).getChildren().get(1).setStyle("" +
                        "-fx-background-color: #D8DEF1;" +
                        "-fx-background-radius: 0;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: Rockwell");
                page = 1;
            }
        });

        // Event Handlers for the footer buttons
        footer.getChildren().get(0).setOnMouseClicked(e -> openAccountForm());
        footer.getChildren().get(1).setOnMouseClicked(e -> depositWithdrawForm("Deposit"));
        footer.getChildren().get(2).setOnMouseClicked(e -> depositWithdrawForm("Withdraw"));
        footer.getChildren().get(3).setOnMouseClicked(e -> transferForm());

        // Displays the main stage to the user
        primaryStage.setScene(new Scene(vBox, windowWidth, windowHeight));
        primaryStage.show();
    }

    public VBox generateInvestmentCalculator() {
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
        calculatorTitle.setStyle("-fx-font-size: 40.0; -fx-font-family: Rockwell");
        title.setStyle("-fx-font-size: 30.0; -fx-font-family: Rockwell");
        initialInvestLabel.setStyle("-fx-font-size: 16.0; -fx-font-family: Rockwell");
        investmentGoalLabel.setStyle("-fx-font-size: 16.0; -fx-font-family: Rockwell");
        interestRateLabel.setStyle("-fx-font-size: 16.0; -fx-font-family: Rockwell");
        timeToMaturation.setStyle("-fx-font-size: 16.0; -fx-font-family: Rockwell");

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

        // Creates a selectable button for each Savings account that the user has
        for (int i = 0; i < 5; i++) {
            Button currAccountSelection = new Button("Savings Account 0" + i);
            currAccountSelection.setFont(new Font("Rockwell", 16));
            currAccountSelection.setStyle("-fx-min-width: 150; -fx-min-height: 50;");
            currAccountSelection.setStyle(styles.savingsButton());
            accounts.getChildren().add(currAccountSelection);
        }

        // Combines all header items to the main header HBox
        header.getChildren().addAll(calculator, details, accounts);

        // Generates Line Graph Depicting estimated Growth of Investment
        XYChart lineGraph = generateGraph();
        lineGraph.setPadding(new Insets(50, 0, 0, 0));

        vBox.setStyle("-fx-background-color: #D8DEF1");
        vBox.setPrefHeight(1080.0);
        vBox.getChildren().addAll(header, lineGraph);

        return vBox;
    }

    public HBox generateAccount(String accountType, Double currentBalance, Double maxBalance, Double investmentRate, Integer accountNum) {
        // Variable Declaration
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
        Label title = new Label("Savings Account");
        Label balance = new Label("Balance: " + currentBalance.toString());
        Label investmentGoal = new Label("Investment Goal: " + maxBalance.toString());
        Label growthRate = new Label("Growth Rate: " + investmentRate.toString());
        Label timeToMaturation = new Label("Time until Maturation: 1 Year");

        if (accountNum % 2 != 0) {
            account.setStyle("-fx-background-color: #BEC3D4");
        }

        // Font Styling for Account Details
        title.setStyle("-fx-font-size: 30.0; -fx-font-weight: bold; -fx-font-family: Rockwell");
        balance.setStyle("-fx-font-size: 16.0; -fx-font-family: Rockwell");
        investmentGoal.setStyle("-fx-font-size: 16.0; -fx-font-family: Rockwell");
        growthRate.setStyle("-fx-font-size: 16.0; -fx-font-family: Rockwell");
        timeToMaturation.setStyle("-fx-font-size: 16.0; -fx-font-family: Rockwell");

        accountDetails.setPadding(new Insets(75, 150, 0, 250));

        // Generate Table displaying details of Account
        accountDetails.getChildren().addAll(
                title,
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
        myAccountNav.setStyle(
                "-fx-background-color: #D8DEF1;" +
                "-fx-background-radius: 0;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: Rockwell"
        );
        investmentCalculatorNav.setStyle(
                "-fx-background-color: #8A93C0;" +
                "-fx-background-radius: 0;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: Rockwell"
        );

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

    public void transferForm() {
        // Variable Declaration
        GridPane selection = new GridPane();
        selection.setVgap(5);
        selection.setHgap(5);

        Label title = new Label("Transfer");
        Label toLabel = new Label("TO");
        Label fromLabel = new Label("FROM");
        Label amountLabel = new Label("AMOUNT");

        ComboBox toSelect = new ComboBox();
        ComboBox fromSelect = new ComboBox();
        TextField amountSelect = new TextField();

        Button transfer = new Button("TRANSFER");

        // Styling for the Form Elements
        selection.setAlignment(Pos.CENTER);

        title.setStyle(styles.labelTitleform());
        toLabel.setStyle(styles.labelForm());
        fromLabel.setStyle(styles.labelForm());
        amountLabel.setStyle(styles.labelForm());

        toSelect.setStyle(styles.selectForm());
        fromSelect.setStyle(styles.selectForm());
        amountSelect.setStyle(styles.selectForm());
        transfer.setStyle(styles.buttonForm());

        // Adds each form item to the main gridpane
        selection.add(title, 1, 0);
        selection.add(toLabel, 0, 1);
        selection.add(toSelect, 1, 1);
        selection.add(fromLabel, 0, 2);
        selection.add(fromSelect, 1, 2);
        selection.add(amountLabel, 0, 3);
        selection.add(amountSelect, 1, 3);
        selection.add(transfer, 1, 4);

        // Styling for the main stage
        selection.setStyle("-fx-background-color: #B8BEDD");

        // Closes the form once the transfer button is selected
        transfer.setOnMouseClicked(e -> form.close());

        // Displays the Transfer Form to the user
        form.setScene(new Scene(selection, 350, 350));
        form.show();
    }

    public void openAccountForm() {
        // Variable Declaration
        GridPane selection = new GridPane();
        selection.setVgap(5);
        selection.setHgap(5);

        Label title = new Label("Open Account");
        Label accountTypeLabel = new Label("Account Type");
        Label accountNameLabel = new Label("Account Name");

        ComboBox accountTypeSelect = new ComboBox();
        TextField accountName = new TextField();

        Button openAccountButton = new Button("OPEN ACCOUNT");

        // Styling for the Form Elements
        selection.setAlignment(Pos.CENTER);

        title.setStyle(styles.labelTitleform());
        accountTypeLabel.setStyle(styles.labelForm());
        accountNameLabel.setStyle(styles.labelForm());

        accountTypeSelect.setStyle(styles.selectForm());
        accountName.setStyle(styles.selectForm());
        openAccountButton.setStyle(styles.buttonForm());

        // Adds each form item to the main gridpane
        selection.add(title, 1, 0);
        selection.add(accountTypeLabel, 0, 1);
        selection.add(accountTypeSelect, 1, 1);
        selection.add(accountNameLabel, 0, 2);
        selection.add(accountName, 1, 2);
        selection.add(openAccountButton, 1, 3);

        // Styling for the main stage
        selection.setStyle("-fx-background-color: #B8BEDD");

        // Closes the form once the transfer button is selected
        openAccountButton.setOnMouseClicked(e -> form.close());

        // Displays the Transfer Form to the user
        form.setScene(new Scene(selection, 400, 300));
        form.show();
    }
    public void depositWithdrawForm(String type) {
        // Variable Declaration
        GridPane selection = new GridPane();
        selection.setVgap(5);
        selection.setHgap(5);

        Label title = new Label(type);
        Label toLabel = new Label();
        Label amountLabel = new Label("AMOUNT");

        // Assigns Destination Label depending on form type
        if (type == "Deposit") { toLabel.setText("TO"); }
        else { toLabel.setText("FROM"); }

        ComboBox toSelect = new ComboBox();
        TextField amountSelect = new TextField();

        Button transfer = new Button(type.toUpperCase());

        // Styling for the Form Elements
        selection.setAlignment(Pos.CENTER);

        title.setStyle(styles.labelTitleform());
        toLabel.setStyle(styles.labelForm());
        amountLabel.setStyle(styles.labelForm());

        toSelect.setStyle(styles.selectForm());
        amountSelect.setStyle(styles.selectForm());
        transfer.setStyle(styles.buttonForm());

        // Adds each form item to the main gridpane
        selection.add(title, 1, 0);
        selection.add(toLabel, 0, 1);
        selection.add(toSelect, 1, 1);
        selection.add(amountLabel, 0, 2);
        selection.add(amountSelect, 1, 2);
        selection.add(transfer, 1, 3);

        // Styling for the main stage
        selection.setStyle("-fx-background-color: #B8BEDD");

        // Closes the form once the transfer button is selected
        transfer.setOnMouseClicked(e -> form.close());

        // Displays the Transfer Form to the user
        form.setScene(new Scene(selection, 350, 350));
        form.show();
    }
}
