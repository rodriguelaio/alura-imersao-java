import enums.URLS;
import models.IMDb;
import parsers.JsonParser;
import utils.HttpClientRequest;

import java.net.URL;
import java.util.List;

public class IMDbSticker {

    private static final String ESC_UNICODE = "\u001B[";

    private static final String MAGENTA_BACKGROUND = "48;2;255;0;255";

    private static final String PURPLE_BACKGROUND = "48;2;142;36;170";

    private static final String BOLD = "1";

    private static final String ANSI_RESET = "0";

    private static final String STAR_UNICODE = "\u2b50";

    private static final String STICKER_FOLDER_PATH = "resources/stickers/imdb/";

    public static void generateTopMoviesStickers() {
        generateImDbStickers(URLS.MOCKED_IMDB_TOP_250_MOVIES, getTopMovies());
    }

    public static void generateTopTvShowsStickers() {
        generateImDbStickers(URLS.MOCKED_IMDB_TOP_250_TVS, getRequestBody(URLS.MOCKED_IMDB_TOP_250_TVS.getUrl()));
    }

    public static void generateMostPopularMoviesStickers() {
        generateImDbStickers(URLS.MOCKED_IMDB_MOST_POPULAR_MOVIES,
            getRequestBody(URLS.MOCKED_IMDB_MOST_POPULAR_MOVIES.getUrl()));
    }

    public static void generateMostPopularTvShowsStickers() {
        generateImDbStickers(URLS.MOCKED_IMDB_MOST_POPULAR_TV,
            getRequestBody(URLS.MOCKED_IMDB_MOST_POPULAR_TV.getUrl()));
    }

    private static void generateImDbStickers(URLS url, String imDbJson) {
        JsonParser parser = url.getJsonParser();
        List<IMDb> iMDbs = parser.parser(imDbJson);
        if (iMDbs == null) {
            return;
        }
        printIMDBsBeautified(iMDbs);
    }

    private static String getTopMovies() {
        String responseBody;
        if ((responseBody = getRequestBody(URLS.IMDB_TOP_250_MOVIES.getUrl())) != null) {
            return responseBody;
        }
        return getRequestBody(URLS.MOCKED_IMDB_TOP_250_MOVIES.getUrl());
    }

    private static String getRequestBody(String url) {
        HttpClientRequest httpClientRequest = new HttpClientRequest(url);
        httpClientRequest.send();
        if (!httpClientRequest.isSuccessful()) {
            return null;
        }
        return httpClientRequest.getBody();
    }

    private static void printIMDBsBeautified(List<IMDb> iMDbs) {
        PersonalIMDbRating personalIMDbRating = new PersonalIMDbRating();
        Double[] personalRate = {0.0};
        iMDbs.forEach(iMDb -> {
            if (personalIMDbRating.isVoting()) {
                personalIMDbRating.rateIMDb(iMDb.title());
                personalRate[0] = personalIMDbRating.getPersonalRating();
            }
            System.out.println("Título: ".concat(iMDb.title()));
            System.out.println("Poster: ".concat(iMDb.imageUrl()));
            printRating("Classificação Geral: ", iMDb.rating(), MAGENTA_BACKGROUND);
            if (personalIMDbRating.isVoting()) {
                printRating("Classificação Própria: ", personalRate[0], PURPLE_BACKGROUND);
                personalIMDbRating.askingKeepRating();
            }
            generateSticker(iMDb);
        });
    }

    private static void generateSticker(IMDb iMDb) {
        try {
            StickerGenerator stickerGenerator = new StickerGenerator(new URL(iMDb.imageUrl()),
                STICKER_FOLDER_PATH,
                iMDb.title(),
                getSubtitleByRate(iMDb.rating()));
            stickerGenerator.createSticker();
        } catch (Exception e) {
            System.out.println("generateSticker Exception: ".concat(e.getMessage()));
        }
    }

    private static String getSubtitleByRate(Double rate) {
        //        Random random = new Random();
        //        System.out.println(random.ints(0, 3).findFirst().getAsInt());
        if (rate >= 9.0) {
            return "sensacional";
        }
        if (rate >= 7.0 && rate <= 8.9) {
            return "bem daora";
        }
        if (rate >= 5.0 && rate <= 6.9) {
            return "até que vai";
        }
        if (rate >= 3.0 && rate <= 4.9) {
            return "meio zuado";
        }
        return "lixo";
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
