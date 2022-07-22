import enums.URLS;
import models.Movie;
import parsers.JsonParser;
import utils.HttpClientRequest;

import java.net.URL;
import java.util.List;

public class App {

    private static final String ESC_UNICODE = "\u001B[";

    private static final String MAGENTA_BACKGROUND = "48;2;255;0;255";

    private static final String PURPLE_BACKGROUND = "48;2;142;36;170";

    private static final String BOLD = "1";

    private static final String ANSI_RESET = "0";

    private static final String STAR_UNICODE = "\u2b50";

    public static void main(String[] args) {

        //        File file = new File("resources/config.properties");
        //        System.out.println(file);
        JsonParser parser = URLS.MOCKED_TOP_250_MOVIES.getJsonParser();
        List<Movie> movies = parser.parser(getTopMovies());
        if (movies == null) {
            return;
        }
        printMoviesBeautified(movies);
    }

    private static String getTopMovies() {
        String responseBody;
        if ((responseBody = getRequestBody(URLS.IMDB_TOP_250_MOVIES.getUrl())) != null) {
            return responseBody;
        }
        return getRequestBody(URLS.MOCKED_TOP_250_MOVIES.getUrl());
    }

    private static String getRequestBody(String url) {
        HttpClientRequest httpClientRequest = new HttpClientRequest(url);
        httpClientRequest.send();
        if (!httpClientRequest.isSuccessful()) {
            return null;
        }
        return httpClientRequest.getBody();
    }

    //    private static void printMoviesBeautified(List<Movie> movies) {
    //        PersonalMovieRating personalMovieRating = new PersonalMovieRating();
    //        Double[] personalRate = {0.0};
    //        movies.forEach(movie -> {
    //            if (personalMovieRating.isVoting()) {
    //                personalMovieRating.rateMovie(movie.getTitle());
    //                personalRate[0] = personalMovieRating.getPersonalRating();
    //            }
    //            System.out.println("Título: ".concat(movie.getTitle()));
    //            System.out.println("Poster: ".concat(movie.getImage()));
    //            printRating("Classificação Geral: ", movie.getRating(), MAGENTA_BACKGROUND);
    //            if (personalMovieRating.isVoting()) {
    //                printRating("Classificação Própria: ", personalRate[0], PURPLE_BACKGROUND);
    //                personalMovieRating.askingKeepRating();
    //            }
    //        });
    //    }

    private static void printMoviesBeautified(List<Movie> movies) {
        PersonalMovieRating personalMovieRating = new PersonalMovieRating();
        Double[] personalRate = {0.0};
        var movie = movies.get(0);
        if (personalMovieRating.isVoting()) {
            personalMovieRating.rateMovie(movie.getTitle());
            personalRate[0] = personalMovieRating.getPersonalRating();
        }
        System.out.println("Título: ".concat(movie.getTitle()));
        System.out.println("Poster: ".concat(movie.getImageUrl()));
        printRating("Classificação Geral: ", movie.getRating(), MAGENTA_BACKGROUND);
        if (personalMovieRating.isVoting()) {
            printRating("Classificação Própria: ", personalRate[0], PURPLE_BACKGROUND);
            personalMovieRating.askingKeepRating();
        }
        generateSticker(movie);
    }

    private static void generateSticker(Movie movie) {
        try {
            StickerGenerator stickerGenerator =
                new StickerGenerator(new URL(movie.getImageUrl()), "resources/stickers/", movie.getTitle(), "guelaio");
            stickerGenerator.createSticker();
        } catch (Exception e) {
            System.out.println("printMoviesBeautified Exception: ".concat(e.getMessage()));
        }
    }

    private static void printRating(String description, Double rate, String rateColor) {
        System.out.println(ESC_UNICODE.concat(rateColor)
            .concat(";")
            .concat(BOLD)
            .concat("m".concat(description))
            .concat(rate.toString())
            .concat(ESC_UNICODE)
            .concat(ANSI_RESET)
            .concat("m"));
        System.out.println(STAR_UNICODE.repeat(rate.intValue()));
    }
}
