package chatclient.scenes;

import javafx.scene.layout.*;
import chatclient.DemoMode;
import chatclient.LoginService;
import chatclient.UserSession;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Class used to represent a Login Screen
 * You may modify the class as you see fit.
 */
public class Login implements Screen {
    private Stage mainStage;
    private TextField usernameField;
    private PasswordField passwordField;
    private Label messageLabel;  // Label to display login status

    public Login(Stage mainStage) {
        this.mainStage = mainStage;
    }

    /**
     * Builds the Login Scene and returns the root of that scene.
     * @return A build Scene
     */
    public Pane build() {
        //TODO NEEDS TO BE REPLACED ONLY HERE SO APP RUNS
        // Create the GridPane layout for the login screen
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create UI elements
        usernameField = new TextField();
        usernameField.setPromptText("Enter Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(this::handleLogin);  // Link to handleLogin

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(this::handleReset);  // Link to handleReset

        messageLabel = new Label();  // Label to show messages

        // Add elements to the grid
        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 0, 2);
        gridPane.add(resetButton, 1, 2);
        gridPane.add(messageLabel, 0, 3, 2, 1);

        if (DemoMode.ENABLED) {
            gridPane.add(
                    new Label("Local Demo Mode - no course server connection"),
                    0,
                    4,
                    2,
                    1
            );
        }

        return gridPane;
    }

    /**
     * used to handle login button click.
     * This function should send username and password to the server
     * and then depending on the response should either display
     * invalid login credentials or transition to the Servers Screen.
     * @param ev Event from button click
     */
    private void handleLogin(ActionEvent ev) {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (DemoMode.ENABLED) {
                String demoUsername = username.trim().isEmpty()
                        ? "demo-user"
                        : username.trim();

                UserSession.getInstance().initSession(
                        demoUsername,
                        1,
                        "local-demo-token"
                );

                messageLabel.setText("Demo login successful!");
                transitionToServersScreen();
                return;
            }


            // Print debug information
            System.out.println("Attempting login with username: " + username);

            // Call the LoginService to perform login
            Optional<String> tokenOptional = LoginService.login(username, password);

            if (tokenOptional.isPresent()) {
                messageLabel.setText("Login successful!");
                System.out.println("Token: " + tokenOptional.get());
                transitionToServersScreen();
            } else {
                messageLabel.setText("Invalid credentials. Please try again.");
                System.out.println("Login failed: Invalid credentials.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("An error occurred. Please try again.");
        }
    }

    /**
     * used to handle reset button click.
     * @param ev Event from button click
     */
    private void handleReset(ActionEvent ev) {
        usernameField.clear();
        passwordField.clear();
        messageLabel.setText("");
    }

    /**
     * Transitions to the Servers screen after successful login.
     */
    private void transitionToServersScreen() {
        Servers serversScreen = new Servers(mainStage);
        mainStage.getScene().setRoot(serversScreen.build());
    }
}
