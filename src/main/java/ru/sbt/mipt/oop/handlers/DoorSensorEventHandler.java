package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.sensors.SensorEvent;

import static ru.sbt.mipt.oop.sensors.SensorEventType.DOOR_CLOSED;
import static ru.sbt.mipt.oop.sensors.SensorEventType.DOOR_OPEN;

public class DoorSensorEventHandler implements SensorEventHandler {
    private final SmartHome smartHome;

    public DoorSensorEventHandler(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void handleEvent(SensorEvent event) {

        // событие от двери

        if (event.getType() != DOOR_OPEN && event.getType() != DOOR_CLOSED) {
            return;
        }

        // событие от двери
        }
    }
}