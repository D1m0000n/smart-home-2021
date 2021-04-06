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

        // представим, что мы не пытаемся открыть открытую дверь)
        boolean openResult = (event.getType() == DOOR_OPEN);
        String eventId = event.getObjectId();

        Action doorAction = (o) -> {
            if (o instanceof Door) {
                Door door = (Door) o;
                if (door.getId().equals(eventId)) {
                    door.setOpen(openResult);
                    System.out.print("Door " + door.getId() + " in room " + " was");
                    System.out.println(openResult ? " opened." : " closed.");
                }
            }
        };

        smartHome.doAction(doorAction);
    }
}