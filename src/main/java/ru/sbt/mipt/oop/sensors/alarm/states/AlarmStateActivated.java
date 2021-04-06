package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.senders.MessageSender;

public class AlarmStateActivated extends AlarmState {

    public AlarmStateActivated(SmartHome smartHome, String code, MessageSender sender) {
        super(smartHome, code, sender);
        this.ignoreEvent = false;
    }

    @Override
    public void activate(String code) {}

    @Override
    public void deactivate(String code) {
        AlarmState state;
        if (this.code.equals(code)) {
            state = new AlarmStateDeactivated(smartHome, code, sender);
        } else {
            state = new AlarmStateAlert(smartHome, code, sender);
            sendMessage();
        }
        smartHome.setState(state);
    }

    @Override
    public void trigger() {
        AlarmState state = new AlarmStateAlert(smartHome, code, sender);
        smartHome.setState(state);
        sendMessage();
    }
}
