package stickers.implementations;

import enums.URLS;
import models.Language;
import stickers.Sticker;
import stickers.StickerGenerator;
import utils.HttpClientRequest;

import java.net.URL;
import java.util.List;

public class LanguageStickerImpl implements Sticker {

    private static final String STICKER_FOLDER_PATH = "resources/stickers/language/";

    public void generateSticker(URLS url) {
        List<Language> languages = url.getJsonParser().parser(getRequestBody(url.getUrl()));
        if (languages == null) {
            return;
        }
        languages.forEach(LanguageStickerImpl::generateSticker);
    }

    private static String getRequestBody(String url) {
        HttpClientRequest httpClientRequest = new HttpClientRequest(url);
        httpClientRequest.send();
        if (!httpClientRequest.isSuccessful()) {
            return null;
        }
        return httpClientRequest.getBody();
    }

    private static void generateSticker(Language language) {
        try {
            System.out.println("Generating ".concat(language.title()).concat("'s image"));
            StickerGenerator.generateSticker(
                new URL(language.imageUrl()),
                STICKER_FOLDER_PATH,
                language.title(),
                "wow");
        } catch (Exception e) {
            System.out.println("generateSticker Exception: ".concat(e.getMessage()));
        }
    }
}
