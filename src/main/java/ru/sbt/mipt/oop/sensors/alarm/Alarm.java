package ru.sbt.mipt.oop.sensors.alarm;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.senders.MessageSender;
import ru.sbt.mipt.oop.senders.SMSMessageSender;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmState;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmStateActivated;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmStateAlert;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmStateDeactivated;

public class Alarm {
    public final SmartHome smartHome;
    AlarmState alarmState;

    public Alarm(SmartHome smartHome, MessageSender sender) {
        this.smartHome = smartHome;
        alarmState = new AlarmStateDeactivated( smartHome, "", sender);
    }

    private void setAlarmState(AlarmState state) {
        alarmState = state;
    }

    public AlarmState getAlarmState() {
        return alarmState;
    }


    public void activate(String code) {
        setAlarmState(alarmState.activate(code));
    }

    public void deactivate(String code) {
        setAlarmState(alarmState.deactivate(code));
    }

    public void trigger() {
        setAlarmState(alarmState.trigger());
    }

    public boolean ignoreEvent() {
        return alarmState instanceof AlarmStateAlert;
    }
}
