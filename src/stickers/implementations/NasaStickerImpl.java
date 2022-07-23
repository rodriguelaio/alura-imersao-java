package stickers.implementations;

import enums.URLS;
import models.Nasa;
import stickers.Sticker;
import utils.HttpClientRequest;

import java.net.URL;
import java.util.List;

public class NasaStickerImpl implements Sticker {

    private static final String STICKER_FOLDER_PATH = "resources/stickers/nasa/";

    public void generateSticker(URLS url) {
        List<Nasa> nasas = url.getJsonParser().parser(getRequestBody(URLS.MOCKED_NASA_APOD.getUrl()));
        if (nasas == null) {
            return;
        }
        nasas.forEach(NasaStickerImpl::generateSticker);
    }

    private static String getRequestBody(String url) {
        HttpClientRequest httpClientRequest = new HttpClientRequest(url);
        httpClientRequest.send();
        if (!httpClientRequest.isSuccessful()) {
            return null;
        }
        return httpClientRequest.getBody();
    }

    private static void generateSticker(Nasa Nasa) {
        try {
            System.out.println("Generating ".concat(Nasa.title()).concat("'s image"));
            StickerGenerator.generateSticker(new URL(Nasa.imageUrl()), STICKER_FOLDER_PATH, Nasa.title(), "wow");
        } catch (Exception e) {
            System.out.println("generateSticker Exception: ".concat(e.getMessage()));
        }
    }
}
