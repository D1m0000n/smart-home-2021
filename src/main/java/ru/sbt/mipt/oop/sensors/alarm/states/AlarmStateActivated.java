package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.SmartHome;

public class AlarmStateActivated extends AlarmState {

    public AlarmStateActivated(SmartHome smartHome, String code) {
        super(smartHome, code);
        this.ignoreEvent = false;
    }

    @Override
    public void activate(String code) {
        throw new RuntimeException("Can't activate activated alarm");
    }

    @Override
    public void deactivate(String code) {
        AlarmState state;
        if (this.code.equals(code)) {
            state = new AlarmStateDeactivated(smartHome, code);
        } else {
            state = new AlarmStateAlert(smartHome, code);
            sendSMS();
        }
        smartHome.setState(state);
    }

    @Override
    public void trigger() {
        AlarmState state = new AlarmStateAlert(smartHome, code);
        smartHome.setState(state);
        sendSMS();
    }
}
