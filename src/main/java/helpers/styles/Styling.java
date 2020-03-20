package main.java.helpers.styles;

import java.util.ArrayList;

import javafx.stage.Screen;

// Styles used in the program
public class Styling {
    public ArrayList<Integer> windowSize() {
        // Variable Declaration
        ArrayList<Integer> window = new ArrayList<Integer>();

        int windowHeight = (int)Screen.getPrimary().getBounds().getHeight() - 50;
        int windowWidth = (int)Screen.getPrimary().getBounds().getWidth() - 50;
        
        //Integer windowWidth = 1024;
        //Integer windowHeight = 900; //TODO Changed Because my screen can't display fit

        window.add(windowWidth);
        window.add(windowHeight);

        // Returns an ArrayList in the form: [Window Width, Window Height]
        return window;
    }
    public String confirmButton() {
        return "-fx-min-height: 50; " +
                "-fx-min-width: 400;" +
                "-fx-font-size: 15.0;" +
                "-fx-border-color: #5E68A0;" +
                "-fx-background-color: #B8BEDD;" +
                "-fx-border-width: 1;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: Rockwell;" +
                "-fx-min-width: 400";
    }

    public String savingsButton() {
        return "-fx-font-size: 15.0;" +
                "-fx-border-color: #5E68A0;" +
                "-fx-background-color: #B8BEDD;" +
                "-fx-border-width: 1;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: Rockwell;" +
                "-fx-pref-width: 200;";
    }

    public String footerButton() {
        return"-fx-font-size: 15.0;" +
              "-fx-border-color: #5E68A0;" +
              "-fx-background-color: #B8BEDD;" +
              "-fx-border-width: 1;" +
              "-fx-font-weight: bold;" +
              "-fx-font-family: Rockwell";
    }

    public String labelTitleform() {
        return "-fx-font-size: 30.0;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: Rockwell;" +
                "-fx-padding: 0 0 15 0;";
    }

    public String labelForm() {
        return "-fx-font-size: 15.0;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: Rockwell;" +
                "-fx-padding: 30 15 15 0;";
    }

    public String selectForm() {
        return "-fx-padding: 5 10 5 10;" +
                "-fx-background-color: #DFE2F2;" +
                "-fx-max-width: 175;" +
                "-fx-min-height: 35";
    }
    public String selectLoginForm() {
        return "-fx-padding: 5 10 5 10;" +
                "-fx-max-width: 175;" +
                "-fx-min-height: 35";
    }

    public String buttonForm() {
        return "-fx-min-height: 35; " +
                "-fx-max-width: 150;" +
                "-fx-font-size: 15.0;" +
                "-fx-border-color: #5E68A0;" +
                "-fx-background-color: #B8BEDD;" +
                "-fx-border-width: 1;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: Rockwell;" +
                "-fx-alignment: center";
    }

    public String navSelected() {
        return "-fx-background-color: #8A93C0;" +
                "-fx-background-radius: 0;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: Rockwell";
    }

    public String navDeselcted() {
        return "-fx-background-color: #D8DEF1;" +
                "-fx-background-radius: 0;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: Rockwell";
    }

    public String accountsList() {
        return "-fx-background: #D8DEF1;" +
                "-fx-background-color: #D8DEF1;" +
                "-fx-min-height: " + (windowSize().get(0) - 220);
    }

    public String loginPane() {
        return  "-fx-background: #D8DEF1;" +
                "-fx-background-color: #D8DEF1;" +
                "-fx-min-height: " + (windowSize().get(0)) +
                "; -fx-alignment: center";
    }

    public String pageHeadText() {
        return "-fx-font-size: 40.0; -fx-font-family: Rockwell";
    }

    public String titleText() {
        return "-fx-font-size: 30.0; -fx-font-family: Rockwell";
    }

    public String labelText() {
        return "-fx-font-size: 16.0; -fx-font-family: Rockwell";
    }
}
