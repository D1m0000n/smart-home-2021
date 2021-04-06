package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.senders.MessageSender;

public class AlarmStateAlert extends AlarmState {

    public AlarmStateAlert(SmartHome smartHome, String code, MessageSender sender) {
        super(smartHome, code, sender);
        this.ignoreEvent = true;
    }

    @Override
    public void activate(String code) {}

    @Override
    public void deactivate(String code) {
        if (this.code.equals(code)) {
            AlarmState state = new AlarmStateDeactivated(smartHome, code, sender);
            smartHome.setState(state);
        } else {
            trigger();
        }
    }

    @Override
    public void trigger() {
        sendMessage();
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
