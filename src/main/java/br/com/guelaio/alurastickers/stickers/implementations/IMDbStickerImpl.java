package br.com.guelaio.alurastickers.stickers.implementations;

import br.com.guelaio.alurastickers.enums.URLS;
import br.com.guelaio.alurastickers.models.IMDb;
import br.com.guelaio.alurastickers.parsers.JsonParser;
import br.com.guelaio.alurastickers.stickers.PersonalIMDbRating;
import br.com.guelaio.alurastickers.stickers.Sticker;
import br.com.guelaio.alurastickers.stickers.StickerGenerator;
import br.com.guelaio.alurastickers.utils.HttpClientRequest;

import java.net.URL;
import java.util.List;

public class IMDbStickerImpl implements Sticker {

    private static final String ESC_UNICODE = "\u001B[";

    private static final String MAGENTA_BACKGROUND = "48;2;255;0;255";

    private static final String PURPLE_BACKGROUND = "48;2;142;36;170";

    private static final String BOLD = "1";

    private static final String ANSI_RESET = "0";

    private static final String STAR_UNICODE = "\u2b50";

    private static final String STICKER_FOLDER_PATH = "resources/stickers/imdb/";

    public void generateSticker(URLS url) {
        generateImDbStickers(url, getRequestBody(url.getUrl()));
    }

    private static void generateImDbStickers(URLS url, String imDbJson) {
        JsonParser parser = url.getJsonParser();
        List<IMDb> iMDbs = parser.parser(imDbJson);
        if (iMDbs == null) {
            return;
        }
        printIMDBsBeautified(iMDbs);
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
            StickerGenerator.generateSticker(new URL(iMDb.imageUrl()),
                STICKER_FOLDER_PATH,
                iMDb.title(),
                getSubtitleByRate(iMDb.rating()));
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
