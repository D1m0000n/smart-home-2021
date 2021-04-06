package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.senders.MessageSender;

import java.util.Objects;

public class AlarmStateDeactivated extends AlarmState {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlarmState state = (AlarmState) o;
        // в выключенном состоянии, код - фиктивное поле
        return ignoreEvent == state.ignoreEvent && smartHome.equals(state.smartHome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(smartHome, ignoreEvent);
    }

    public AlarmStateDeactivated(SmartHome smartHome, String code, MessageSender sender) {
        super(smartHome, code, sender);
        this.ignoreEvent = false;
    }

    @Override
    public void activate(String code) {
        AlarmState alarmState = new AlarmStateActivated(smartHome, code, sender);
        smartHome.setState(alarmState);
    }

    @Override
    public void deactivate(String code) {}

    @Override
    public void trigger() {
        // ничего не делаем, потому что сигнализация выключена
    }
}
