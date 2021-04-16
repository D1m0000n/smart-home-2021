package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.senders.MessageSender;
import ru.sbt.mipt.oop.sensors.alarm.Alarm;

import java.util.Objects;

public class AlarmStateDeactivated extends AlarmState {

    public AlarmStateDeactivated(SmartHome smartHome, String code, MessageSender sender) {
        super(smartHome, code, sender);
    }

    @Override
    public AlarmState activate(String code) {
        return new AlarmStateActivated(smartHome, code, sender);
    }

    @Override
    public AlarmState deactivate(String code) {
        throw new RuntimeException("Can't deactivate deactivated alarm");
    }

    @Override
    public AlarmState trigger() {
        // ничего не делаем, потому что сигнализация выключена
        return this;
    }
}
