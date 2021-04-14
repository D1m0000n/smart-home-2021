package ru.sbt.mipt.oop.sensors.alarm.states;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.senders.MessageSender;
import ru.sbt.mipt.oop.sensors.alarm.Alarm;

public class AlarmStateAlert extends AlarmState {

    public AlarmStateAlert(Alarm alarm, String code, MessageSender sender) {
        super(alarm, code, sender);
    }

    @Override
    public void activate(String code) {
        throw new RuntimeException("Can't activate alarmed alarm");
    }

    @Override
    public void deactivate(String code) {
        if (this.code.equals(code)) {
            AlarmState state = new AlarmStateDeactivated(alarm, code, sender);
            alarm.setAlarmState(state);
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

        alarm.smartHome.doAction(lightSwitch);
    }
}
