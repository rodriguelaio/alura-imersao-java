import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    private String title;
    private String image;

    @JsonProperty("imDbRating")
    private Double rating;

    @JsonProperty("imDbRatingCount")
//    @JsonSubTypes.Type(Double.class)
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
