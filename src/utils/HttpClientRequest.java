package utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientRequest {

    private final String url;

    private HttpResponse<String> response;

    private String body;

    private boolean isSuccessful;

    public HttpClientRequest(String url) {
        this.url = url;
    }

    public HttpResponse<String> getResponse() {
        return this.response;
    }

    public String getBody() {
        return this.body;
    }

    public boolean isSuccessful() {
        return this.isSuccessful;
    }

    private void setResponse(HttpResponse<String> response) {
        this.response = response;
    }

    private void setBody(String body) {
        this.body = body;
    }

    private void setSuccessful(boolean successful) {
        this.isSuccessful = successful;
    }

    public void send() {
        try {
            var uri = URI.create(this.url);
            HttpClient client = java.net.http.HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder(uri).GET().build();
            setResponse(client.send(request, HttpResponse.BodyHandlers.ofString()));
            setBody(response.body());
            setSuccessful(response.statusCode() == 200);
        } catch (Exception e) {
            System.out.println("HttpClientRequest Exception: ".concat(e.getMessage()));
        }
    }
}
