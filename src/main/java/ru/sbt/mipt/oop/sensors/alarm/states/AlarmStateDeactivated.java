package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.SmartHome;

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

    public AlarmStateDeactivated(SmartHome smartHome, String code) {
        super(smartHome, code);
        this.ignoreEvent = false;
    }

    @Override
    public void activate(String code) {
        AlarmState alarmState = new AlarmStateActivated(smartHome, code);
        smartHome.setState(alarmState);
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
