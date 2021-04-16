package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.handlers.*;
import ru.sbt.mipt.oop.handlers.decorators.AlarmSensorEventDecorator;
import ru.sbt.mipt.oop.readers.JSONSmartHomeReader;
import ru.sbt.mipt.oop.readers.SmartHomeReader;
import ru.sbt.mipt.oop.sensors.SensorEventCreatorImpl;

import java.util.Arrays;

public class Application {

    public static void main(String... args) {
        // считываем состояние дома из файла
        String filename = "smart-home-1.js";
        SmartHomeReader reader = new JSONSmartHomeReader(filename);
        SmartHome smartHome = reader.readSmartHome();
        // начинаем цикл обработки событий
        SensorEventHandler sensorEventHandler = new AlarmSensorEventDecorator(
                smartHome, new GeneralSensorEventHandler(
                smartHome,
                Arrays.asList(
                        new LightSensorEventHandler(smartHome),
                        new DoorSensorEventHandler(smartHome),
                        new HallDoorSensorEventHandler(smartHome))));
        EventLoopProcessor eventLoopProcessor = new EventLoopProcessor(new SensorEventCreatorImpl(), new EventProcessor(smartHome, sensorEventHandler));
        eventLoopProcessor.loopEvents();
    }
}
