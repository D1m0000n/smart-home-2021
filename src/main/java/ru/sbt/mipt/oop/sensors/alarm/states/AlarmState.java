package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.senders.MessageSender;

import java.util.Objects;

public abstract class AlarmState {
    public AlarmState(SmartHome smartHome, String code, MessageSender sender) {
        this.smartHome = smartHome;
        this.code = code;
        this.sender = sender;
    }

    public final SmartHome smartHome;
    public final String code;
    public final MessageSender sender;
    public boolean ignoreEvent;

    public abstract void activate(String code);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlarmState state = (AlarmState) o;
        return ignoreEvent == state.ignoreEvent && smartHome.equals(state.smartHome) && Objects.equals(code, state.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(smartHome, code, ignoreEvent);
    }

    public abstract void deactivate(String code);

    public abstract void trigger();

    public void sendMessage() {
        sender.sendMessage();
    }
}
