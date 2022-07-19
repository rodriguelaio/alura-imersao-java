import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Properties;

public class App {

    private static final String ESC_UNICODE = "\u001B[";

    private static final String MAGENTA_BACKGROUND = "48;2;255;0;255";

    private static final String BOLD = "1";

    private static final String ANSI_RESET = "0";

    private static final String STAR_UNICODE = "\u2b50";

    public static void main(String[] args) {

        List<Movie> movies = JsonParser.parser(getTopMovies());
        if (movies == null)
            return;
        movies.forEach(movie -> {
            System.out.println("Título: ".concat(movie.getTitle()));
            System.out.println("Poster: ".concat(movie.getImage()));
            System.out.println(ESC_UNICODE.concat(MAGENTA_BACKGROUND)
                .concat(";")
                .concat(BOLD)
                .concat("mClassificação: ")
                .concat(movie.getRating().toString())
                .concat(ESC_UNICODE)
                .concat(ANSI_RESET)
                .concat("m"));
            System.out.println(STAR_UNICODE.repeat(movie.getRating().intValue()));
        });
    }

    private static String getTopMovies() {
        String responseBody;
        var url = getImdDbUrl();
        if (url != null && (responseBody = getResponseBodyFromApi(url)) != null) {
            return responseBody;
        }
        return getResponseBodyFromApi(getMockedApiUrl());
    }

    private static String getImdDbUrl() {
        var imDbEndpoint = getConfigPropertiesValue("imDbUrl");
        var imDbToken = getConfigPropertiesValue("imDbToken");
        if (imDbEndpoint != null && imDbToken != null) {
            return imDbEndpoint.concat("/").concat(imDbToken);
        }
        return null;
    }

    private static String getMockedApiUrl() {
        return getConfigPropertiesValue("mockedTop250MoviesApiUrl");
    }

    private static String getConfigPropertiesValue(String propertyKey) {
        try {
            FileInputStream fileInputStream = new FileInputStream("resources/config.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties.getProperty(propertyKey);
        } catch (Exception e) {
            return null;
        }
    }

    private static String getResponseBodyFromApi(String url) {
        try {
            var uri = URI.create(url);
            HttpClient client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder(uri).GET().build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
