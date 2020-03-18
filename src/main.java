import helpers.Account;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import helpers.styles.Styling;
import helpers.Generator;
import helpers.Forms;
import java.util.ArrayList;

public class main extends Application {
    // Global Variable Declaration
    Integer page = 0;
    Styling styles = new Styling();
    Generator generate = new Generator();
    Forms forms = new Forms();
    VBox accountsVBox = new VBox(); //TODO Temporarily moved to global variable, needs to be moved back and used from database?
    Integer index = 0; //TODO Not sure how else to alternate between colour schemes while keeping track
    Stage form =  new Stage();
    VBox vBox = new VBox();

    Integer windowWidth = (Integer) styles.windowSize().get(0);
    Integer windowHeight = (Integer) styles.windowSize().get(1);

    protected ArrayList<Account> accountsList = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Variable Declaration
        primaryStage.setTitle("Finance Application");
        //VBox accountsVBox = new VBox(); //TODO Needs to be added back
        HBox navigation = generate.generateNavigation();
        HBox footer = generate.generateFooter();
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

        // TEMP Displays information about each Account open for the user
        for (int i = 0; i < 2; i++) {
            Account tempAcc = new Account("Savings", "Savings 0" + i, 500.0,
                    1500.0, i);
            index++;
            accountsList.add(tempAcc);
            accountsVBox.getChildren().add(generate.generateAccount(tempAcc));
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
        VBox investmentCalculator = generate.generateInvestmentCalculator();

        // Allows switching between the two navigation tabs
        navigation.getChildren().get(0).setOnMouseClicked(e -> {
            if (page != 0) {
                vBox.getChildren().set(2, accounts);
                vBox.getChildren().add(footer);
                // Styling for the Navigation tab
                ((HBox) vBox.getChildren().get(1)).getChildren().get(0).setStyle(styles.navDeselcted());
                ((HBox) vBox.getChildren().get(1)).getChildren().get(1).setStyle(styles.navSelected());
                page = 0;
            }
        });
        navigation.getChildren().get(1).setOnMouseClicked(e -> {
            if (page != 1) {
                vBox.getChildren().set(2, investmentCalculator);
                vBox.getChildren().remove(3);
                // Styling for the Navigation tab
                ((HBox) vBox.getChildren().get(1)).getChildren().get(0).setStyle(styles.navSelected());
                ((HBox) vBox.getChildren().get(1)).getChildren().get(1).setStyle(styles.navDeselcted());
                page = 1;
            }
        });

        // Event Handlers for the footer buttons
        footer.getChildren().get(0).setOnMouseClicked(e -> {
            index = forms.openAccountForm(index, accountsList, accountsVBox);
        });
        footer.getChildren().get(1).setOnMouseClicked(e -> forms.depositWithdrawForm("Deposit"));
        footer.getChildren().get(2).setOnMouseClicked(e -> forms.depositWithdrawForm("Withdraw"));
        footer.getChildren().get(3).setOnMouseClicked(e -> forms.transferForm(accountsList, accountsVBox));

        vBox.setOnMouseClicked(e -> {
            // Variable Declaration
            // Obtains index of selected account
            Integer accountIndex = (int) e.getY() / 350;
            Account account = accountsList.get(accountIndex);

            // Open Form to edit account
            updateAccount(account, primaryStage);
>>>>>>> branch-B(Account)
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

        // Displays the main stage to the user
        primaryStage.setScene(new Scene(vBox, windowWidth, windowHeight));
        primaryStage.show();
    }

    public void updateAccount(Account account, Stage primaryStage) {
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
            // Closes the form once the user submits their changes
            form.close();
        });

        // Updates accounts list on main page when user edits an account
        form.setOnHiding(e -> {
            vBox.getChildren().set(2, generate.updateList(accountsList, primaryStage));
        });

        // Displays the Edit Account Form to the user
        form.setScene(new Scene(selection, 450, 450));
        form.show();
    }
}
