package ru.sbt.mipt.oop.sensors.alarm;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.senders.MessageSender;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmState;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmStateActivated;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmStateAlert;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmStateDeactivated;

public class Alarm {
    private final SmartHome smartHome;

    public Alarm(SmartHome smartHome) {
        this.smartHome = smartHome;
    }


    public void activate(String code) {
        AlarmState alarmState = smartHome.getState();

        if (alarmState instanceof AlarmStateActivated) {
            throw new RuntimeException("Can't activate activated alarm");
        } else if (alarmState instanceof AlarmStateAlert) {
            throw new RuntimeException("Can't activate alarmed alarm");
        } else if (alarmState instanceof AlarmStateDeactivated) {
            alarmState.activate(code);
        } else {
            throw new RuntimeException("Wrong alarm state");
        }
    }

    public void deactivate(String code) {
        AlarmState alarmState = smartHome.getState();

        if (alarmState instanceof AlarmStateActivated) {
            alarmState.deactivate(code);
        } else if (alarmState instanceof AlarmStateDeactivated) {
            throw new RuntimeException("Can't deactivate deactivated alarm");
        } else if (alarmState instanceof AlarmStateAlert) {
            alarmState.deactivate(code);
        } else {
            throw new RuntimeException("Wrong alarm state");
        }
    }

    public void trigger() {
        smartHome.getState().trigger();
    }

    public boolean ignoreEvent() {
        return smartHome.getState().ignoreEvent;
    }
}
