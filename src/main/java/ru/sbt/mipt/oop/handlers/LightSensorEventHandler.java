package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.sensors.SensorEvent;

import java.util.Collection;

import static ru.sbt.mipt.oop.sensors.SensorEventType.LIGHT_OFF;
import static ru.sbt.mipt.oop.sensors.SensorEventType.LIGHT_ON;

public class LightSensorEventHandler implements SensorEventHandler {

    private final SensorEvent event;
    private final SmartHome smartHome;

    public LightSensorEventHandler(SmartHome smartHome, SensorEvent event) {
        this.smartHome = smartHome;
        this.event = event;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        // событие от источника света
        if (event.getType() != LIGHT_ON && event.getType() != LIGHT_OFF) {
            return;

        }

        for (Room room : smartHome.getRooms()) {
            for (Light light : room.getLights()) {
                if (light.getId().equals(event.getObjectId())) {
                    if (event.getType() == LIGHT_ON) {
                        handleLightOn(room, light);
                    } else {
                        handleLightOff(room, light);
                    }
                }
            }

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
