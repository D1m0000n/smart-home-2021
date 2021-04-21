package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.handlers.*;
import ru.sbt.mipt.oop.handlers.decorators.AlarmSensorEventDecorator;
import ru.sbt.mipt.oop.readers.JSONSmartHomeReader;
import ru.sbt.mipt.oop.readers.SmartHomeReader;
import ru.sbt.mipt.oop.senders.MessageSender;
import ru.sbt.mipt.oop.senders.SMSMessageSender;
import ru.sbt.mipt.oop.sensors.SensorEventCreatorImpl;
import ru.sbt.mipt.oop.sensors.alarm.Alarm;

import java.util.Arrays;

public class Application {

    public static void main(String... args) {
        // считываем состояние дома из файла
        String filename = "smart-home-1.js";
        SmartHomeReader reader = new JSONSmartHomeReader(filename);
        SmartHome smartHome = reader.readSmartHome();
        // начинаем цикл обработки событий
        MessageSender sender = new SMSMessageSender();
        SensorEventHandler sensorEventHandler = new AlarmSensorEventDecorator(
                new CompositeSensorEventHandler(
                smartHome,
                Arrays.asList(
                        new LightSensorEventHandler(smartHome),
                        new DoorSensorEventHandler(smartHome),
                        new HallDoorSensorEventHandler(smartHome))),
                new Alarm(smartHome, sender));
        EventLoopProcessor eventLoopProcessor = new EventLoopProcessor(new SensorEventCreatorImpl(), new EventProcessor(smartHome, sensorEventHandler));
        eventLoopProcessor.loopEvents();
    }
}
