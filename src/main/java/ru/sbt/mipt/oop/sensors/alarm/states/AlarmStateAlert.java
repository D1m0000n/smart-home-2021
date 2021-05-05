package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.senders.MessageSender;
import ru.sbt.mipt.oop.sensors.alarm.Alarm;

public class AlarmStateAlert extends AlarmState {

    public AlarmStateAlert(SmartHome smartHome, String code, MessageSender sender) {
        super(smartHome, code, sender);
    }

    @Override
    public AlarmState activate(String code) {
        throw new RuntimeException("Can't activate alarmed alarm");
    }

    @Override
    public AlarmState deactivate(String code) {
        if (this.code.equals(code)) {
            return new AlarmStateDeactivated(smartHome, code, sender);
        } else {
            return trigger();
        }
    }

    @Override
    public AlarmState trigger() {
        sender.sendMessage();
        flashingLight();
        return this;
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
