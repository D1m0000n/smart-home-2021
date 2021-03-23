package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.sensors.SensorEvent;

public class AlarmStateDeactivated extends AlarmState {

    public AlarmStateDeactivated(SmartHome smartHome, String code) {
        super(smartHome, code);
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
