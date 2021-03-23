package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.sensors.SensorEvent;

import java.util.Collection;

import static ru.sbt.mipt.oop.sensors.SensorEventType.DOOR_CLOSED;
import static ru.sbt.mipt.oop.sensors.SensorEventType.DOOR_OPEN;

public class DoorSensorEventHandler implements SensorEventHandler {
    private final SensorEvent event;
    private final SmartHome smartHome;

    public DoorSensorEventHandler(SmartHome smartHome, SensorEvent event) {
        this.event = event;
        this.smartHome = smartHome;
    }

    @Override
    public void handleEvent() {
        // событие от двери
        if (event.getType() != DOOR_OPEN && event.getType() != DOOR_CLOSED) {
            return;
        }

        // представим, что мы не пытаемся открыть открытую дверь)
        boolean openResult = (event.getType() == DOOR_OPEN);
        String eventId = event.getObjectId();
        
//        Action lightOffAction = (o)->{
//            if (o instanceof Light) {
//                Light light = (Light) o;
//                light.setOn(false);
//            }
//        };
        
        Action doorAction = (o)-> {
//            if (o instanceof Room) {
//                Room room = (Room) o;
//                if (room.getName().equals("hall")) {
//                    smartHome.doAction(lightOffAction);
//                }
//            }
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