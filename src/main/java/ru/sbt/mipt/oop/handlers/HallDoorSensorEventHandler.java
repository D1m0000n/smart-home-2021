package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.sensors.SensorEvent;
import static ru.sbt.mipt.oop.sensors.SensorEventType.DOOR_CLOSED;


public class HallDoorSensorEventHandler implements SensorEventHandler {
    private final SensorEvent event;
    private final SmartHome smartHome;

    public HallDoorSensorEventHandler(SmartHome smartHome, SensorEvent event) {
        this.event = event;
        this.smartHome = smartHome;
    }

    @Override
    public void handleEvent() {

        // Интересует только случай закрывания двери в холле
        if (event.getType() != DOOR_CLOSED) {
            return;
        }

        Action lightOffAction = (o)->{
            if (o instanceof Light) {
                Light light = (Light) o;
                light.setOn(false);
            }
        };

        Action hallDoorAction = (o)-> {
            if (o instanceof Room) {
                Room room = (Room) o;
                if (room.getName().equals("hall")) {
                    smartHome.doAction(lightOffAction);
                }
            }
        };

        smartHome.doAction(hallDoorAction);
    }
}
