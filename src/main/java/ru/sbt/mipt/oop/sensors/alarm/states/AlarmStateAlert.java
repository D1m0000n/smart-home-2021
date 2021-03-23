package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.sensors.SensorEvent;
import ru.sbt.mipt.oop.sensors.alarm.AlarmSensorEvent;

public class AlarmStateAlert extends AlarmState {

    public AlarmStateAlert(SmartHome smartHome, String code) {
        super(smartHome, code);
    }

    @Override
    public void activate(String code) {
        throw new RuntimeException("Can't activate alarmed alarm");
    }

    @Override
    public void deactivate(String code) {
        if (this.code.equals(code)) {
            AlarmState state = new AlarmStateDeactivated(smartHome, code);
            smartHome.setState(state);
        } else {
            // TODO: моргать лампочками
            sendSMS();
        }
    }

    @Override
    public void trigger() {

    }
}
