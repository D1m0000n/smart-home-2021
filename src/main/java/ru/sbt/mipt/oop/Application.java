package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.handlers.DoorSensorEventHandler;
import ru.sbt.mipt.oop.handlers.HallDoorSensorEventHandler;
import ru.sbt.mipt.oop.handlers.LightSensorEventHandler;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.readers.JSONSmartHomeReader;
import ru.sbt.mipt.oop.readers.SmartHomeReader;
import ru.sbt.mipt.oop.sensors.SensorEventCreatorImpl;

import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String... args) {
        // считываем состояние дома из файла
        String filename = "smart-home-1.js";
        SmartHomeReader reader = new JSONSmartHomeReader(filename);
        SmartHome smartHome = reader.readSmartHome();
        // начинаем цикл обработки событий
        eventLoopProcessor.loopEvents();
    }
}
