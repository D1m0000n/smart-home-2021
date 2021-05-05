package ru.sbt.mipt.oop.readers;

import com.google.gson.Gson;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.readers.SmartHomeReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONSmartHomeReader implements SmartHomeReader {

    private final String filename;

    public JSONSmartHomeReader(String filename) {
        this.filename = filename;
    }

    @Override
    public SmartHome readSmartHome() {
        Gson gson = new Gson();
        String json;
        try {
            json = new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            throw new RuntimeException("Could not read file" + filename, e);
        }
        return gson.fromJson(json, SmartHome.class);
    }
}
