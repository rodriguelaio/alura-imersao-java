package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Movie(String title, @JsonProperty("image") String imageUrl, @JsonProperty("imDbRating") Double rating,
                    @JsonProperty("imDbRatingCount") Double ratingCount) {

}
