package chatclient.scenes;


import chatclient.UserSession;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.websocket.*;

public class ChatRoom implements Screen {
    private final Stage stage;
    private final String roomName;
    private UserSession userSession;
    private Session session;
    private ListView<String> users;
    private ListView<String> messages;

    public ChatRoom(Stage primaryStage) {
        this(primaryStage, "Chat Room");
    }

    public ChatRoom(Stage primaryStage, String roomName) {
        this.stage = primaryStage;
        this.roomName = roomName;
        messages = new ListView<>();
        users = new ListView<>();
        this.userSession = UserSession.getInstance();
    }

    /**
     * Builds the Scene for viewing a chat room.
     * @return
     */
    public Pane build() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label title = new Label("Chat Room: " + roomName);
        Label mode = new Label("Local Demo Mode - messages are stored only in memory");

        users.setPrefHeight(90);
        users.getItems().setAll("demo-user", "Alice", "Bob");

        messages.setPrefHeight(240);
        messages.getItems().setAll(
                "Alice: Welcome to the local demo room!",
                "Bob: The original course server is not required here."
        );

        TextField messageField = new TextField();
        messageField.setPromptText("Type a message");

        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> sendMessage(messageField.getText(), event, messageField));

        Button leaveButton = new Button("Leave Room");
        leaveButton.setOnAction(event ->
                stage.getScene().setRoot(new Servers(stage).build())
        );

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> {
            userSession.clearSession();
            stage.getScene().setRoot(new Login(stage).build());
        });

        HBox messageArea = new HBox(10, messageField, sendButton);
        HBox navigationArea = new HBox(10, leaveButton, logoutButton);

        root.getChildren().addAll(
                title,
                mode,
                new Label("Users"),
                users,
                new Label("Messages"),
                messages,
                messageArea,
                navigationArea
        );

        return root;
    }

    /**
     * Initializes a connection of a WebSocket to the chat server.
     * @param roomId which room connecting to
     * @param userId who's connecting to the room
     * @param roomName the name of the room.
     * @return whether or not the connection was made.
     */
    public boolean initWebsocket(final int roomId,final int userId, final String roomName) {
        return false;
    }

    /**
     * Set a reference to the WebSocket Session
     * @param s reference to WebSocket Session.
     */
    public void setSession(Session s){
        this.session = s;
    }

    /**
     * handles an incoming message from the Chat Server.
     * Gson is needed here to parse the Incoming Message
     * to a WebSocketResponse Type.
     * Depending on the value of event, this function should
     * handling a regular chat message, a join event,
     * and a leave event.
     * @param msg
     */
    public void handleIncomingMessage(final String msg){

    }

    /**
     * Send a message to the Chat Server.
     * The message must be in the correct format
     * when being sent to the server.
     * @param message to be send
     * @param e ActionEvent object.
     */
    public void sendMessage(String message, ActionEvent e) {
        String trimmedMessage = message == null ? "" : message.trim();

        if (trimmedMessage.isEmpty()) {
            return;
        }

        if (chatclient.DemoMode.ENABLED) {
            messages.getItems().add(userSession.getUsername() + ": " + trimmedMessage);
        }
    }

    private void sendMessage(String message, ActionEvent e, TextField messageField) {
        sendMessage(message, e);
        messageField.clear();
    }

    /**
     * send message to server using the current WebSocket
     * session.
     * @param message to be sent.
     */
    public void sendMessage(final String message) {
        if (this.session != null && this.session.isOpen()) {
            this.session.getAsyncRemote().sendText(message);
        }
    }

    /**
     * fetch current users in a active room.
     * Users should be added to the  users ListView
     */
    public void loadUsersPane() {


    }

    /**
     * fetch current messages in a active room.
     * Messages should be added to the messages ListView
     */
    public void loadMessages() {

    }

}
