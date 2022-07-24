package enums;

import parsers.JsonParser;
import parsers.implementations.IMDbJsonParserImpl;
import parsers.implementations.NasaJsonParserImpl;
import stickers.Sticker;
import stickers.implementations.IMDbStickerImpl;
import stickers.implementations.NasaStickerImpl;
import utils.ConfigPropertiesReader;

public enum URLS {
    IMDB_TOP_250_MOVIES("imDbUrl", new IMDbJsonParserImpl(), new IMDbStickerImpl()),
    MOCKED_IMDB_TOP_250_MOVIES("mockedTop250MoviesApiUrl", new IMDbJsonParserImpl(), new IMDbStickerImpl()),
    MOCKED_IMDB_TOP_250_TVS("mockedTop250TVApisUrl", new IMDbJsonParserImpl(), new IMDbStickerImpl()),
    MOCKED_IMDB_MOST_POPULAR_MOVIES("mockedMostPopularMoviesApiUrl", new IMDbJsonParserImpl(), new IMDbStickerImpl()),
    MOCKED_IMDB_MOST_POPULAR_TV("mockedMostPopularTVsApiUrl", new IMDbJsonParserImpl(), new IMDbStickerImpl()),

    MOCKED_NASA_APOD("mockedNasaApodApi", new NasaJsonParserImpl(), new NasaStickerImpl());

    private final String url;

    private final JsonParser jsonParser;

    private final Sticker sticker;

    private static final String imDbTokenPropertyKey = "imDbToken";

    URLS(String propertyKey, JsonParser jsonParser, Sticker sticker) {
        this.url = getFinalUrl(propertyKey);
        this.jsonParser = jsonParser;
        this.sticker = sticker;
    }

    public String getUrl() {
        return this.url;
    }

    public JsonParser getJsonParser() {
        return this.jsonParser;
    }

    public Sticker getSticker() {
        return this.sticker;
    }

    private String getFinalUrl(String propertyKey) {
        if (propertyKey.toLowerCase().contains("imdb")) {
            return ConfigPropertiesReader.getConfigPropertiesValue(propertyKey)
                .concat(ConfigPropertiesReader.getConfigPropertiesValue(imDbTokenPropertyKey));
        }
        return ConfigPropertiesReader.getConfigPropertiesValue(propertyKey);
    }
}
