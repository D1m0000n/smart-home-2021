package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.senders.MessageSender;
import ru.sbt.mipt.oop.sensors.alarm.Alarm;

import java.util.Objects;

public abstract class AlarmState {
    Alarm alarm;
    public AlarmState(Alarm alarm, String code, MessageSender sender) {
        this.alarm = alarm;
        this.code = code;
        this.sender = sender;
    }

    public final String code;
    public final MessageSender sender;

    public abstract void activate(String code);

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

    public abstract void deactivate(String code);

    public abstract void trigger();
}
