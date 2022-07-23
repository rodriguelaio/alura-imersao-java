package enums;

import parsers.JsonParser;
import parsers.implementations.IMDbJsonParserImpl;
import parsers.implementations.NasaJsonParserImpl;
import utils.ConfigPropertiesReader;

public enum URLS {
    IMDB_TOP_250_MOVIES("imDbUrl", new IMDbJsonParserImpl()),
    MOCKED_IMDB_TOP_250_MOVIES("mockedTop250MoviesApiUrl", new IMDbJsonParserImpl()),
    MOCKED_IMDB_TOP_250_TVS("mockedTop250TVApisUrl", new IMDbJsonParserImpl()),
    MOCKED_IMDB_MOST_POPULAR_MOVIES("mockedMostPopularMoviesApiUrl", new IMDbJsonParserImpl()),
    MOCKED_IMDB_MOST_POPULAR_TV("mockedMostPopularTVsApiUrl", new IMDbJsonParserImpl()),

    MOCKED_NASA_APOD("mockedNasaApodApi", new NasaJsonParserImpl());

    private final String url;

    private final JsonParser jsonParser;

    private static final String imDbTokenPropertyKey = "imDbToken";

    URLS(String propertyKey, JsonParser jsonParser) {
        this.url = getFinalUrl(propertyKey);
        this.jsonParser = jsonParser;
    }

    public String getUrl() {
        return this.url;
    }

    public JsonParser getJsonParser() {
        return this.jsonParser;
    }

    private String getFinalUrl(String propertyKey) {
        if (propertyKey.toLowerCase().contains("imdb")) {
            return ConfigPropertiesReader.getConfigPropertiesValue(propertyKey)
                .concat(ConfigPropertiesReader.getConfigPropertiesValue(imDbTokenPropertyKey));
        }
        return ConfigPropertiesReader.getConfigPropertiesValue(propertyKey);
    }
}
