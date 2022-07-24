package br.com.guelaio.alurastickers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Nasa(String title, @JsonProperty("url") String imageUrl) {

}
