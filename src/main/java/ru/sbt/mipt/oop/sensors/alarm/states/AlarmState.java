package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.senders.MessageSender;
import ru.sbt.mipt.oop.sensors.alarm.Alarm;

import java.util.Objects;

public abstract class AlarmState {

    public final String code;
    public final MessageSender sender;
    public final SmartHome smartHome;

    public AlarmState(SmartHome smartHome, String code, MessageSender sender) {
        this.code = code;
        this.sender = sender;
        this.smartHome = smartHome;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlarmState state = (AlarmState) o;
        return Objects.equals(code, state.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    public abstract AlarmState activate(String code);

    public abstract AlarmState deactivate(String code);

    public abstract AlarmState trigger();
}
