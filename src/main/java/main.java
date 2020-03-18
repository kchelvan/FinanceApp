package main.java;

import helpers.Account;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.java.helpers.styles.Styling;
import main.java.helpers.Generator;
import main.java.helpers.Forms;
import java.util.ArrayList;

public class main extends Application {
    // Global Variable Declaration
    Integer page = 0;
    Styling styles = new Styling();
    Generator generate = new Generator();
    Forms forms = new Forms();
    VBox accountsVBox = new VBox(); //TODO Temporarily moved to global variable, needs to be moved back and used from database?
    Integer index = 0; //TODO Not sure how else to alternate between colour schemes while keeping track
    VBox vBox = new VBox();

    Integer windowWidth = styles.windowSize().get(0);
    Integer windowHeight = styles.windowSize().get(1);
    boolean emptyAccount = false;

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

        if (accountsList.size() == 0) {
            Label noAccounts = new Label("No Accounts Available");
            noAccounts.setStyle(styles.labelText());
            noAccounts.setTranslateX(Double.valueOf(styles.windowSize().get(0)) / 2.35);
            noAccounts.setPadding(new Insets(15));
            accountsVBox.getChildren().add(noAccounts);
            emptyAccount = true;
        }

        // TEMP Displays information about each Account open for the user
//        for (int i = 0; i < 2; i++) {
//            Account tempAcc = new Account("Savings", "Savings 0" + i, 500.0,
//                    1500.0, i);
//            index++;
//            accountsList.add(tempAcc);
//            accountsVBox.getChildren().add(generate.generateAccount(tempAcc));
//        }

        // Styling for the VBox containing the different user accounts
        accountsVBox.setMinHeight(accountsList.size() * 350);

        // Allows the user to scroll through the available accounts
        accounts.setContent(accountsVBox);
        // Styling for scroll pane
        accounts.setStyle("-fx-background: #D8DEF1; -fx-background-color: #D8DEF1");
        accounts.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        vBox.getChildren().addAll(menuBar, navigation, accounts , footer);

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
                // Creates an instance of the investment calculator
                VBox investmentCalculator = generate.generateInvestmentCalculator(accountsList);
                vBox.getChildren().set(2, investmentCalculator);
                vBox.getChildren().remove(3);
                // Styling for the Navigation tab
                ((HBox) vBox.getChildren().get(1)).getChildren().get(0).setStyle(styles.navSelected());
                ((HBox) vBox.getChildren().get(1)).getChildren().get(1).setStyle(styles.navDeselcted());
                page = 1;
            }
        });

        accountsVBox.setOnMouseClicked(e -> {
            // Variable Declaration1
            // Obtains index of selected account
            Integer accountIndex = (int) e.getY() / 350;
            Account account = accountsList.get(accountIndex);

            // Open Form to edit account
            generate.updateAccount(account, primaryStage, accountsList, vBox);
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

        // Event Handlers for the footer buttons
        footer.getChildren().get(0).setOnMouseClicked(e -> {
            if (accountsList.size() > 0) { emptyAccount = false; }
            index = forms.openAccountForm(index, accountsList, accountsVBox, emptyAccount);
        });
        footer.getChildren().get(1).setOnMouseClicked(e -> forms.depositWithdrawForm("Deposit", accountsList, accountsVBox));
        footer.getChildren().get(2).setOnMouseClicked(e -> forms.depositWithdrawForm("Withdraw", accountsList, accountsVBox));
        footer.getChildren().get(3).setOnMouseClicked(e -> forms.transferForm(accountsList, accountsVBox));

        // Displays the main stage to the user
        primaryStage.setScene(new Scene(vBox, windowWidth, windowHeight));
        primaryStage.show();
    }
}
