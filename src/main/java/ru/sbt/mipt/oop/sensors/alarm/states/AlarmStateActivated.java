package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.senders.MessageSender;
import ru.sbt.mipt.oop.sensors.alarm.Alarm;

public class AlarmStateActivated extends AlarmState {

    public AlarmStateActivated(Alarm alarm, String code, MessageSender sender) {
        super(alarm, code, sender);
    }

    @Override
    public void activate(String code) {
        throw new RuntimeException("Can't activate activated alarm");
    }

    @Override
    public void deactivate(String code) {
        AlarmState state;
        if (this.code.equals(code)) {
            state = new AlarmStateDeactivated(alarm, code, sender);
        } else {
            state = new AlarmStateAlert(alarm, code, sender);
            sendMessage();
        }
        alarm.setAlarmState(state);
    }

    @Override
    public void trigger() {
        AlarmState state = new AlarmStateAlert(alarm, code, sender);
        alarm.setAlarmState(state);
        sendMessage();
    }
}
