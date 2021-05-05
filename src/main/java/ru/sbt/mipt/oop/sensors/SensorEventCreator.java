package ru.sbt.mipt.oop.sensors;

import ru.sbt.mipt.oop.sensors.SensorEvent;

public interface SensorEventCreator {
    SensorEvent getNextSensorEvent();
}
