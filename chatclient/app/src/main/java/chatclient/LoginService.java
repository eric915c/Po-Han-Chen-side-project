package chatclient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.Optional;
import com.google.gson.JsonObject;

public class LoginService {

    // The URL for the login API endpoint
    private static final String LOGIN_URL = "https://csc413.ajsouza.com/users/login/app";
    /**
     * Sends a login request to the server with the provided username and password.
     * If successful, returns the token from the server.
     *
     * @param username The user's username.
     * @param password The user's password.
     * @return An Optional containing the token if login is successful, empty otherwise.
     */
    public static Optional<String> login(String username, String password) {
        try {
            // Create a JSON object for the request body with username and password.
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("username", username);
            requestBody.addProperty("password", password);

            // Build the HTTP POST request with headers and body.
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(LOGIN_URL))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(10))  // Set a timeout for the request
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            // Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            // Send the HTTP request and handle the response.
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Log the HTTP response status and body.
            System.out.println("HTTP Status Code: " + response.statusCode());
            System.out.println("HTTP Response Body: " + response.body());

            // Check if the response status code is 200 (OK).
            if (response.statusCode() == 200) {
                // Parse the JSON response from the server.
                JsonObject jsonResponse = com.google.gson.JsonParser.parseString(response.body()).getAsJsonObject();

                // Check if the login was successful.
                if ("success".equals(jsonResponse.get("status").getAsString())) {
                    // Extract and return the token from the JSON response.
                    String token = jsonResponse.get("token").getAsString();
                    System.out.println("Login successful! Token: " + token);
                    return Optional.of(token);
                } else {
                    // Print the error message if login failed.
                    System.out.println("Login failed: " + jsonResponse.get("message").getAsString());
                }
            } else {
                System.out.println("Unexpected HTTP response: " + response.statusCode());
            }
        } catch (HttpTimeoutException e) {
            System.out.println("Request timed out. Please try again.");
        } catch (Exception e) {
            System.out.println("An error occurred during login: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
