package enums;

import utils.ConfigPropertiesReader;

public enum URLS {
    IMDB_TOP_250_MOVIES("imDbUrl"),
    MOCKED_TOP_250_MOVIES("mockedTop250MoviesApiUrl"),
    MOCKED_TOP_250_TVS("mockedTop250TVApisUrl"),
    MOCKED_MOST_POPULAR_MOVIES("mockedMostPopularMoviesApiUrl"),
    MOCKED_MOST_POPULAR_TV("mockedMostPopularTVsApiUrl");

    private final String url;

    private static final String imDbTokenPropertyKey = "imDbToken";

    URLS(String propertyKey) {
        this.url = getFinalUrl(propertyKey);
    }

    public String getUrl() {
        return this.url;
    }

    private String getFinalUrl(String propertyKey) {
        if (propertyKey.toLowerCase().contains("imdb")) {
            return ConfigPropertiesReader.getConfigPropertiesValue(propertyKey)
                .concat(ConfigPropertiesReader.getConfigPropertiesValue(imDbTokenPropertyKey));
        }
        return ConfigPropertiesReader.getConfigPropertiesValue(propertyKey);
    }
}
