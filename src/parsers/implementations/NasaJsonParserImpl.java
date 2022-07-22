package parsers.implementations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.NasaImage;
import parsers.JsonParser;

import java.util.List;

public class NasaJsonParserImpl implements JsonParser {

    public List<NasaImage> parser(String json) {
        try {
            var objectMapper = new ObjectMapper();
            var jsonNode = objectMapper.readTree(json);
            return objectMapper.readValue(jsonNode.toString(), new TypeReference<>() {
            });
        } catch (Exception e) {
            System.out.println("NasaJsonParserImpl Exception: ".concat(e.getMessage()));
            return null;
        }
    }
}
