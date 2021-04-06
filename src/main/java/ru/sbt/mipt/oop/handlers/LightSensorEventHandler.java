package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.sensors.SensorEvent;

import static ru.sbt.mipt.oop.sensors.SensorEventType.LIGHT_OFF;
import static ru.sbt.mipt.oop.sensors.SensorEventType.LIGHT_ON;

public class LightSensorEventHandler implements SensorEventHandler {
    private final SmartHome smartHome;

    public LightSensorEventHandler(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        // событие от источника света
        if (event.getType() != LIGHT_ON && event.getType() != LIGHT_OFF) {
            return;
        }

        // представим, что мы не пытаемся включить включенный свет
        boolean turnResult = (event.getType() == LIGHT_ON);
        String eventId = event.getObjectId();

        Action action = (o) -> {
            if (o instanceof Light) {
                Light light = (Light) o;
                String lightId = light.getId();
                if (lightId.equals(eventId)) {
                    light.setOn(turnResult);
                    System.out.print("Light " + light.getId() + " was turned ");
                    System.out.println(turnResult ? "on." : "off.");
                }
            }
        };
        smartHome.doAction(action);
    }
}
