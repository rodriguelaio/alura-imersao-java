package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Movie;

import java.util.List;

public class JsonParser {

    public static List<Movie> parser(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            return objectMapper.readValue(jsonNode.get("items").toString(), new TypeReference<>() {
            });
        } catch (Exception e) {
            System.out.println("JsonParser Exception: ".concat(e.getMessage()));
            return null;
        }
    }
}
