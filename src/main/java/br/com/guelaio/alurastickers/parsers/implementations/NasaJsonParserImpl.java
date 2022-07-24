package br.com.guelaio.alurastickers.parsers.implementations;

import br.com.guelaio.alurastickers.models.Nasa;
import br.com.guelaio.alurastickers.parsers.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class NasaJsonParserImpl implements JsonParser {

    public List<Nasa> parser(String json) {
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
