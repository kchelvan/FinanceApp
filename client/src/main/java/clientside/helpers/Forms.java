package clientside.helpers;

import clientside.financeClient;
import clientside.helpers.styles.Styling;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Forms {
    // Global Variable Declaration
    // Single form used to allow only one form to be open at a time
    Stage form =  new Stage();
    Styling styles = new Styling();
    Generator generate = new Generator();

    public void loginForm(financeClient client, ArrayList<Account> accountsList, Stage primaryStage, VBox vBox) {
        // Variable Declaration for Login Form Values
        GridPane loginPane = new GridPane();

        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");
        Label errorLabel = new Label();

        TextField usernameText = new TextField();
        PasswordField passwordText = new PasswordField();

        Button login = new Button("Login");
        Button signup = new Button("Sign Up");

        // Assigns Title for Login Form
        form.setTitle("Login Form");

        // Styling for Login Form Elements
        login.setStyle(styles.buttonForm());
        signup.setStyle(styles.buttonForm());

        usernameLabel.setStyle(styles.labelForm());
        passwordLabel.setStyle(styles.labelForm());
        errorLabel.setStyle(styles.labelForm());

        usernameText.setStyle(styles.selectLoginForm());
        passwordText.setStyle(styles.selectLoginForm());

        loginPane.setHgap(15);

        loginPane.setStyle(styles.loginPane());

        // Adds each form item to the main gridpane
        loginPane.add(usernameLabel, 0, 0);
        loginPane.add(usernameText, 1, 0);
        loginPane.add(passwordLabel, 0, 1);
        loginPane.add(passwordText, 1, 1);
        loginPane.add(login, 0, 2);
        loginPane.add(signup, 1, 2);
        loginPane.add(errorLabel, 0, 3, 2, 1);

        // Event Handlers for Login Buttons
        login.setOnMouseClicked(e -> {
            try {
                if (!(usernameText.getText().isEmpty() || passwordText.getText().isEmpty())) {
                    // Displays error message if the login api returns an error message
                    String errorMSG = client.login(usernameText.getText(), passwordText.getText());
                    if (errorMSG.contains("ERROR:")){
                        errorLabel.setText(errorMSG);
                    } else {
                        // If login is successful, display the main home page
                        for(Account account : client.getAccountList()) {
                            accountsList.add(account);
                        }
                        // Displays the home page of the app and closes the login form
                        generate.updateList(client, accountsList, primaryStage, vBox);
                        form.close();
                        primaryStage.show();
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        signup.setOnMouseClicked(e -> {
            try {
                if (!(usernameText.getText().isEmpty() || passwordText.getText().isEmpty())) {
                    // Displays error message if the signup api returns an error message
                    String errorMSG = client.register(usernameText.getText(), passwordText.getText());
                    // If signup is successful, display the main home page
                    if (errorMSG.contains("ERROR:")) {
                        errorLabel.setText(errorMSG);
                    } else {
                        // Displays the home page of the app and closes the login form
                        client.save();
                        generate.updateList(client, accountsList, primaryStage, vBox);
                        form.close();
                        primaryStage.show();
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
        });

        // Displays the Login Form to the user
        form.setScene(new Scene(loginPane, 400, 300));
        form.show();
    }

    public Integer openAccountForm(financeClient client, Integer pIndex, ArrayList<Account> accountsList,
                                   VBox accountsVBox, boolean emptyAccount, Stage primaryStage, VBox vBox) {
        // Variable Declaration
        String[] accountTypes = {"Savings", "Checking"};
        Account account = new Account();
        final Integer index = pIndex;

        GridPane selection = new GridPane();
        selection.setVgap(5);
        selection.setHgap(5);

        Label title = new Label("Open Account");
        Label accountTypeLabel = new Label("Account Type*");
        Label accountNameLabel = new Label("Account Name");

        ComboBox<String> accountTypeSelect = new ComboBox<>(FXCollections.observableArrayList(accountTypes));
        TextField accountName = new TextField();

        Button openAccountButton = new Button("OPEN ACCOUNT");

        // Assigns Title for Open Account Form
        form.setTitle("Open Account Form");

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

        // Closes the form once the Open Account button is selected
        openAccountButton.setOnMouseClicked(e -> {
            if (accountTypeSelect.getValue() != null)
            {
                // Assigns default value to account name
                if (accountName.getText().isEmpty()) {
                    accountName.setText(accountTypeSelect.getValue() + " Account");
                }
                // Generates New Account
                account.addAccount(accountTypeSelect.getValue(), accountName.getText());
                account.setAccountNumber(index);
                accountsList.add(account);
                account.setTimeToMaturation();
                // Clears "No Accounts Header" if accounts exist
                if (emptyAccount) { accountsVBox.getChildren().set(0, generate.generateAccount(account)); }
                else { accountsVBox.getChildren().add(generate.generateAccount(account)); }
                // Updates and closes form with form values
                generate.updateList(client, accountsList, primaryStage, vBox);
                client.getUser().addAccount(account);
                client.save();
                form.close();
            }
        });

        // Displays the Open Account Form to the user
        form.setScene(new Scene(selection, 400, 300));
        form.show();

        // Updates index
        return index + 1;
    }

    public void depositWithdrawForm(financeClient client, String type, ArrayList<Account> accountsList,
                                    VBox accountsVBox, Stage primaryStage, VBox vBox) {
        // Variable Declaration
        GridPane selection = new GridPane();
        selection.setVgap(5);
        selection.setHgap(5);

        Label title = new Label(type);
        Label toLabel = new Label();
        Label amountLabel = new Label("AMOUNT");

        List<String> accountNames = accountsList.stream().map(Account::getAccountName).collect(Collectors.toList());
        ComboBox<String> toSelect = new ComboBox<>(FXCollections.observableList(accountNames));

        // Assigns Destination Label depending on form type
        if (type == "Deposit") { toLabel.setText("TO"); }
        else { toLabel.setText("FROM"); }

        TextField amountSelect = new TextField();

        Button transfer = new Button(type.toUpperCase());

        // Assigns Title for Deposit/Withdraw Form
        form.setTitle(type + " Form");

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
        transfer.setOnMouseClicked(e ->{
            // Assigns index of selected account
            int accIndex = accountNames.indexOf(toSelect.getValue());

            // Performs a Deposit or Withdrawal depending on the account type
            if (type == "Deposit") { accountsList.get(accIndex).deposit(Double.parseDouble(amountSelect.getText())); }
            else { accountsList.get(accIndex).withdraw(Double.parseDouble(amountSelect.getText())); }

            // Updates accountsList when account is updated
            accountsVBox.getChildren().clear();
            for(Account tempAcc:accountsList) {
                accountsVBox.getChildren().add(generate.generateAccount(tempAcc));
            }
            generate.updateList(client, accountsList, primaryStage, vBox);
            client.save();
            form.close();
        });

        // Displays the Deposit/Withdraw Form to the user
        form.setScene(new Scene(selection, 350, 350));
        form.show();
    }

    public void transferForm(financeClient client,ArrayList<Account> accountsList, VBox accountsVBox,
                             Stage primaryStage, VBox vBox) {
        // Variable Declaration
        GridPane selection = new GridPane();
        selection.setVgap(5);
        selection.setHgap(5);

        Label title = new Label("Transfer");
        Label toLabel = new Label("TO");
        Label fromLabel = new Label("FROM");
        Label amountLabel = new Label("AMOUNT");

        List<String> accountNames = accountsList.stream().map(Account::getAccountName).collect(Collectors.toList());

        ComboBox<String> toSelect = new ComboBox<>(FXCollections.observableList(accountNames));
        ComboBox<String> fromSelect = new ComboBox<>(FXCollections.observableList(accountNames));
        TextField amountSelect = new TextField();

        Button transfer = new Button("TRANSFER");
        // Assigns Title for Transfer Form
        form.setTitle("Transfer Form");

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
        transfer.setOnMouseClicked(e ->{
            // Assigns Index for selected accounts
            int toIndex = accountNames.indexOf(toSelect.getValue());
            int fromIndex = accountNames.indexOf(fromSelect.getValue());

            // Performs transferal between two selected accounts
            accountsList.get(fromIndex).withdraw(Double.parseDouble(amountSelect.getText()));
            accountsList.get(toIndex).deposit(Double.parseDouble(amountSelect.getText()));

            // Updates Accounts List
            accountsVBox.getChildren().clear();
            for(Account tempAcc:accountsList) {
                accountsVBox.getChildren().add(generate.generateAccount(tempAcc));
            }
            generate.updateList(client, accountsList, primaryStage, vBox);
            client.save();
            form.close();
        });

        // Displays the Transfer Form to the user
        form.setScene(new Scene(selection, 350, 350));
        form.show();
    }
}
