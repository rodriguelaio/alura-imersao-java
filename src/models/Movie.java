package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    private String title;
    private String image;

    @JsonProperty("imDbRating")
    private Double rating;

    @JsonProperty("imDbRatingCount")
    private Double ratingCount;

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public Double getRating() {
        return rating;
    }

    public Double getRatingCount() {
        return ratingCount;
    }
}
