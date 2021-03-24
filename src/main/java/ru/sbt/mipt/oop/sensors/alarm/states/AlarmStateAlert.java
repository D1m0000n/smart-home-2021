package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;

public class AlarmStateAlert extends AlarmState {

    public AlarmStateAlert(SmartHome smartHome, String code) {
        super(smartHome, code);
        this.ignoreEvent = true;
    }

    @Override
    public void activate(String code) {
        throw new RuntimeException("Can't activate alarmed alarm");
    }

    @Override
    public void deactivate(String code) {
        if (this.code.equals(code)) {
            AlarmState state = new AlarmStateDeactivated(smartHome, code);
            smartHome.setState(state);
        } else {
            trigger();
        }
    }

    @Override
    public void trigger() {
        sendSMS();
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
