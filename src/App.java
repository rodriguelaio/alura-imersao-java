import models.Movie;

import java.util.List;

public class App {

    private static final String ESC_UNICODE = "\u001B[";

    private static final String MAGENTA_BACKGROUND = "48;2;255;0;255";

    private static final String BOLD = "1";

    private static final String ANSI_RESET = "0";

    private static final String STAR_UNICODE = "\u2b50";

    public static void main(String[] args) {

        List<Movie> movies = JsonParser.parser(getTopMovies());
        if (movies == null) {
            return;
        }
        printMoviesBeautified(movies);
    }

    private static String getTopMovies() {
        String responseBody;
        if ((responseBody = getRequestBody(getImdDbUrl())) != null) {
            return responseBody;
        }
        return getRequestBody(getMockedApiUrl());
    }

    private static String getRequestBody(String url) {
        HttpClientRequest httpClientRequest = new HttpClientRequest(url);
        httpClientRequest.send();
        if (!httpClientRequest.isSuccessful()) {
            return null;
        }
        return httpClientRequest.getBody();
    }

    private static String getImdDbUrl() {
        var imDbEndpoint = ConfigPropertiesReader.getConfigPropertiesValue("imDbUrl");
        var imDbToken = ConfigPropertiesReader.getConfigPropertiesValue("imDbToken");
        if (imDbEndpoint == null || imDbToken == null) {
            return null;
        }
        return imDbEndpoint.concat("/").concat(imDbToken);
    }

    private static String getMockedApiUrl() {
        return ConfigPropertiesReader.getConfigPropertiesValue("mockedTop250MoviesApiUrl");
    }

    private static void printMoviesBeautified(List<Movie> movies) {
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
}
