package enums;

import utils.ConfigPropertiesReader;
import parsers.JsonParser;
import parsers.implementations.MoviesJsonParserImpl;

public enum URLS {
    IMDB_TOP_250_MOVIES("imDbUrl", new MoviesJsonParserImpl()),
    MOCKED_TOP_250_MOVIES("mockedTop250MoviesApiUrl", new MoviesJsonParserImpl()),
    MOCKED_TOP_250_TVS("mockedTop250TVApisUrl", new MoviesJsonParserImpl()),
    MOCKED_MOST_POPULAR_MOVIES("mockedMostPopularMoviesApiUrl", new MoviesJsonParserImpl()),
    MOCKED_MOST_POPULAR_TV("mockedMostPopularTVsApiUrl", new MoviesJsonParserImpl());

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
