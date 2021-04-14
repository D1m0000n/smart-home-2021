package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.senders.MessageSender;
import ru.sbt.mipt.oop.sensors.alarm.Alarm;

import java.util.Objects;

public class AlarmStateDeactivated extends AlarmState {

    public AlarmStateDeactivated(Alarm alarm, String code, MessageSender sender) {
        super(alarm, code, sender);
    }

    @Override
    public void activate(String code) {
        AlarmState alarmState = new AlarmStateActivated(alarm, code, sender);
        alarm.setAlarmState(alarmState);
    }

    @Override
    public void deactivate(String code) {
        throw new RuntimeException("Can't deactivate deactivated alarm");
    }

    @Override
    public void trigger() {
        // ничего не делаем, потому что сигнализация выключена
    }
}
