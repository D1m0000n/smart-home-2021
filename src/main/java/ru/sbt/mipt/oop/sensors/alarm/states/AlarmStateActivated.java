package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.senders.MessageSender;
import ru.sbt.mipt.oop.sensors.alarm.Alarm;

public class AlarmStateActivated extends AlarmState {

    public AlarmStateActivated(SmartHome smartHome, String code, MessageSender sender) {
        super(smartHome, code, sender);
    }

    @Override
    public AlarmState activate(String code) {
        throw new RuntimeException("Can't activate activated alarm");
    }

    @Override
    public AlarmState deactivate(String code) {
        AlarmState state;
        if (this.code.equals(code)) {
            state = new AlarmStateDeactivated(smartHome, code, sender);
        } else {
            state = new AlarmStateAlert(smartHome, code, sender);
            sender.sendMessage();
        }
        return state;
    }

    @Override
    public AlarmState trigger() {
        AlarmState state = new AlarmStateAlert(smartHome, code, sender);
        sender.sendMessage();
        return state;
    }
}
