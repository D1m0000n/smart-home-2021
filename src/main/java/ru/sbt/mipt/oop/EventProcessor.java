package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.handlers.*;
import ru.sbt.mipt.oop.handlers.decorators.AlarmSensorEventDecorator;
import ru.sbt.mipt.oop.sensors.SensorEvent;

import java.util.Arrays;

public class EventProcessor {
    public final SmartHome smartHome;
    public final SensorEventHandler sensorEventHandler;

    public EventProcessor(SmartHome smartHome, SensorEventHandler sensorEventHandler) {
        this.smartHome = smartHome;
        this.sensorEventHandler = sensorEventHandler;
    }

    public void processEvent(SensorEvent event) {
        System.out.println("Got event: " + event);
        sensorEventHandler.handleEvent(event);
    }
}
