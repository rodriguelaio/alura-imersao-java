package br.com.guelaio.alurastickers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record IMDb(String title, @JsonProperty("image") String imageUrl, @JsonProperty("imDbRating") Double rating,
                   @JsonProperty("imDbRatingCount") Double ratingCount) {

}
