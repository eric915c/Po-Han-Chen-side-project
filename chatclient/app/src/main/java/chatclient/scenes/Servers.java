package chatclient.scenes;

import chatclient.DemoMode;
import chatclient.UserSession;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Class used to represent a Server Listing Screen.
 * We should be able to create a new room and join a
 * current room from this screen.
 */

public class Servers implements Screen {
    private final Stage mainStage;
    private ListView<String> servers;
    private final HttpClient client = HttpClient.newHttpClient();

    public Servers(Stage s) {
        this.mainStage = s;
        this.servers = new ListView<>();
    }

    /**
     * Builds the Server listing Screen
     * @return root to the Server Listing Screen
     */
    public Pane build() {
        VBox root = new VBox(10); // Vertical layout with spacing of 10
        Label titleLabel = new Label("Available Rooms");

        servers.setPrefHeight(300);
        servers.setPlaceholder(new Label("No rooms available"));

        Button refreshButton = new Button("Refresh Rooms");
        refreshButton.setOnAction(this::refreshServerList);

        Button connectButton = new Button("Join Room");
        connectButton.setOnAction(this::connectToRoom);

        Button createButton = new Button("Create New Room");
        createButton.setOnAction(this::showCreateRoomForm);

        root.getChildren().addAll(titleLabel, servers, refreshButton, connectButton, createButton);

        if (DemoMode.ENABLED) {
            refreshServerList(null);
        }

        return root;
    }

    /**
     * Fetch active servers from the Chat Server
     * @param ev
     */
    private void refreshServerList(ActionEvent ev) {
        if (DemoMode.ENABLED) {
            servers.getItems().setAll(DemoMode.ROOMS);
            return;
        }

        try {
            String token = UserSession.getInstance().getToken();  // Ensure token is valid

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://csc413.ajsouza.com/api/rooms"))
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonResponse = com.google.gson.JsonParser.parseString(response.body()).getAsJsonObject();
                JsonArray rooms = jsonResponse.getAsJsonArray("rooms");

                servers.getItems().clear();  // Clear previous room list

                if (rooms.size() > 0) {
                    for (int i = 0; i < rooms.size(); i++) {
                        JsonObject room = rooms.get(i).getAsJsonObject();
                        String roomName = room.get("room_name").getAsString();
                        servers.getItems().add(roomName);  // Add room to ListView
                    }
                    System.out.println("Rooms refreshed successfully.");
                } else {
                    System.out.println("No rooms available.");
                }
            } else {
                System.err.println("Failed to fetch rooms. Status: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Connect to the current room selected in the servers listing.
     * Should open a new websocket, and also fetch messages and users for
     * the connecting room.
     * @param ev
     */
    private void connectToRoom(ActionEvent ev) {
        String selectedRoom = servers.getSelectionModel().getSelectedItem();

        if (selectedRoom == null) {
            System.out.println("No room selected.");
            return;
        }

        System.out.println("Connecting to room: " + selectedRoom);

        if (DemoMode.ENABLED) {
            ChatRoom chatRoom = new ChatRoom(mainStage, selectedRoom);
            mainStage.getScene().setRoot(chatRoom.build());
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Connected");
        alert.setHeaderText(null);
        alert.setContentText("Connected to room: " + selectedRoom);
        alert.showAndWait();
    }

    /**
     * Show form to create a new room, this can be a popup OR a UI
     * component you show and hide when needed.
     * @param ev
     */
    private void showCreateRoomForm(ActionEvent ev) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create New Room");
        dialog.setHeaderText("Enter a name for the new room:");

        dialog.showAndWait().ifPresent(this::handleCreateRoom);
    }

    /**
     * Creates a new room on the Chat Server.
     * @param roomName The name of the room to be created.
     */
    private void handleCreateRoom(String roomName) {
        if (DemoMode.ENABLED) {
            String trimmedName = roomName == null ? "" : roomName.trim();

            if (!trimmedName.isEmpty() && !servers.getItems().contains(trimmedName)) {
                servers.getItems().add(trimmedName);
            }
            return;
        }

        try {
            String token = UserSession.getInstance().getToken();

            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("room_name", roomName);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://csc413.ajsouza.com/api/rooms"))
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Room created successfully.");
                refreshServerList(new ActionEvent());
            } else {
                System.err.println("Failed to create room. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
