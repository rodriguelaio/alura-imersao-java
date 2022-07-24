package br.com.guelaio.alurastickers.parsers;

import java.util.List;

public interface JsonParser {

    <T> List<T> parser(String json);
}
