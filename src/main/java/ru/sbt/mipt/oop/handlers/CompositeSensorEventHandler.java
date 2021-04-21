package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.sensors.SensorEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class CompositeSensorEventHandler implements SensorEventHandler {
    private final SmartHome smartHome;
    private final List<SensorEventHandler> handlers;

    public SmartHome getSmartHome() {
        return smartHome;
    }

    public CompositeSensorEventHandler(SmartHome smartHome, List<SensorEventHandler> handlers) {
        this.smartHome = smartHome;
        this.handlers = handlers;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        handlers.forEach(sensorEventHandler -> sensorEventHandler.handleEvent(event));
    }
}
