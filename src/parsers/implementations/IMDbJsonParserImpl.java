package parsers.implementations;

import parsers.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.IMDb;

import java.util.List;

public class IMDbJsonParserImpl implements JsonParser {

    public List<IMDb> parser(String json) {
        try {
            var objectMapper = new ObjectMapper();
            var jsonNode = objectMapper.readTree(json);
            return objectMapper.readValue(jsonNode.get("items").toString(), new TypeReference<>() {
            });
        } catch (Exception e) {
            System.out.println("NasaJsonParserImpl Exception: ".concat(e.getMessage()));
            return null;
        }
    }
}
