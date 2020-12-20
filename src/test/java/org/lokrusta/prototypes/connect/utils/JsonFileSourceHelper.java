package org.lokrusta.prototypes.connect.utils;

import lombok.Getter;
import org.lokrusta.prototypes.connect.impl.ApiHelper;
import org.lokrusta.prototypes.connect.impl.JsonFileSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class JsonFileSourceHelper<T> {

    private final JsonFileSource jsonFileSource;
    private final List<T> values;

    public JsonFileSourceHelper(Class<T> clazz) throws IOException, URISyntaxException {
        Path testFile = Paths.get(ClassLoader.getSystemResource("person-source.json").toURI());
        values = Files.readAllLines(testFile)
                .stream()
                .map(s -> ApiHelper.fromString(s, clazz))
                .collect(Collectors.toList());
        jsonFileSource = new JsonFileSource(testFile, clazz);
    }

    public List<T> getValues() {
        return values;
    }
}
