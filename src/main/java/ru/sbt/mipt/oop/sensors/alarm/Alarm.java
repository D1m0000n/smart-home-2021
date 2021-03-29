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
    private final AlarmState alarmState;
    private final MessageSender sender;

    public Alarm(SmartHome smartHome, MessageSender sender) {
        this.smartHome = smartHome;
        this.alarmState = smartHome.getState();
        this.sender = sender;
    }


    public void activate(String code) {
        if (alarmState instanceof AlarmStateActivated) {
            activateActivatedAlarm(code);
        } else if (alarmState instanceof AlarmStateDeactivated) {
            activateDeactivatedAlarm(code);
        } else if (alarmState instanceof AlarmStateAlert) {
            activateAlertedAlarm(code);
        } else {
            throw new RuntimeException("Wrong alarm state");
        }
    }

    public void deactivate(String code) {
        if (alarmState instanceof AlarmStateActivated) {
            deactivateActivatedAlarm(code);
        } else if (alarmState instanceof AlarmStateDeactivated) {
            deactivateDeactivatedAlarm(code);
        } else if (alarmState instanceof AlarmStateAlert) {
            deactivateAlertedAlarm(code);
        } else {
            throw new RuntimeException("Wrong alarm state");
        }
    }

    public void trigger() {
        if (alarmState instanceof AlarmStateActivated) {
            triggerActivatedAlarm();
        } else if (alarmState instanceof AlarmStateDeactivated) {
            triggerDeactivatedAlarm();
        } else if (alarmState instanceof AlarmStateAlert) {
            triggerAlertedAlarm();
        } else {
            throw new RuntimeException("Wrong alarm state");
        }
    }

    public boolean ignoreEvent() {
        return alarmState.ignoreEvent;
    }

    private void activateActivatedAlarm(String code) {
        throw new RuntimeException("Can't activate activated alarm");
    }

    private void deactivateActivatedAlarm(String code) {
        AlarmState state;
        if (alarmState.code.equals(code)) {
            state = new AlarmStateDeactivated(smartHome, code);
        } else {
            state = new AlarmStateAlert(smartHome, code);
            sender.sendMessage();
        }
        smartHome.setState(state);
    }

    private void activateDeactivatedAlarm(String code) {
        AlarmState alarmState = new AlarmStateActivated(smartHome, code);
        smartHome.setState(alarmState);
    }

    private void deactivateDeactivatedAlarm(String code) {
        throw new RuntimeException("Can't deactivate deactivated alarm");
    }

    private void activateAlertedAlarm(String code) {
        throw new RuntimeException("Can't activate alarmed alarm");
    }

    private void deactivateAlertedAlarm(String code) {
        if (alarmState.code.equals(code)) {
            AlarmState state = new AlarmStateDeactivated(smartHome, code);
            smartHome.setState(state);
        } else {
            trigger();
        }
    }

    private void triggerActivatedAlarm() {
        AlarmState state = new AlarmStateAlert(smartHome, alarmState.code);
        smartHome.setState(state);
        sender.sendMessage();
    }

    private void triggerDeactivatedAlarm() {
        // ничего не делаем, потому что сигнализация выключена
    }

    private void triggerAlertedAlarm() {
        sender.sendMessage();
        flashingLight();
    }

    private void flashingLight() {
        Action lightSwitch = (component) -> {
            if (component instanceof Light) {
                Light light = (Light) component;
                boolean switchLightState = !light.isOn();

                // имитируем моргание лампочки
                light.setOn(switchLightState);
                light.setOn(!switchLightState);
            }
        };

        smartHome.doAction(lightSwitch);
    }
}
