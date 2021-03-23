package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.sensors.SensorEvent;

public abstract class AlarmState {
    public AlarmState(SmartHome smartHome, String code) {
        this.smartHome = smartHome;
        this.code = code;
    }

    public final SmartHome smartHome;
    public final String code;

    public abstract void activate(String code);

    public abstract void deactivate(String code);

    public abstract void trigger();

    public void sendSMS() {
        System.out.println("Sending sms");
    }
}
