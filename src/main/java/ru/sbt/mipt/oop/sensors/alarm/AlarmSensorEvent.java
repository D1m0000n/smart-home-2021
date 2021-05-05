package ru.sbt.mipt.oop.sensors.alarm;

import ru.sbt.mipt.oop.sensors.SensorEvent;
import ru.sbt.mipt.oop.sensors.SensorEventType;

public class AlarmSensorEvent extends SensorEvent {
    private final String alarmCode;

    public AlarmSensorEvent(SensorEventType type, String alarmCode) {
        super(type, "0");
        this.alarmCode = alarmCode;
    }

    public String getAlarmCode() {
        return alarmCode;
    }
}
