package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.sensors.SensorEvent;

public interface SensorEventHandler {
    void handleEvent(SensorEvent event);
}
