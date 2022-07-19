import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Movie;

import java.util.List;

public class JsonParser {

//    private static final Pattern REGEX_ITEMS = Pattern.compile(".*\\[(.+)\\].*");
    //
    //    private static final Pattern REGEX_JSON_ATTRIBUTES = Pattern.compile("\"(.+?)\":\"(.*?)\"");

    public static List<Movie> parser(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            return objectMapper.readValue(jsonNode.get("items").toString(), new TypeReference<>() {
            });
        } catch (Exception e) {
            return null;
        }

        //        Matcher matcher = REGEX_ITEMS.matcher(json);
        //        if (!matcher.find()) {
        //            throw new IllegalArgumentException("No items found!");
        //        }
        //
        //        String[] items = matcher.group(1).split("\\}.\\{");
        //        List<Map<String, String>> data = new ArrayList<>();
        //        for (String item : items) {
        //            Map<String, String> itemAttributes = new HashMap<>();
        //            Matcher matcherJsonAttributes = REGEX_JSON_ATTRIBUTES.matcher(item);
        //            while (matcherJsonAttributes.find()) {
        //                String key = matcherJsonAttributes.group(1);
        //                String value = matcherJsonAttributes.group(2);
        //                itemAttributes.put(key, value);
        //            }
        //            data.add(itemAttributes);
        //        }
        //        return data;
    }
}
