package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.sensors.SensorCommand;
import ru.sbt.mipt.oop.sensors.SensorEvent;

import java.util.Collection;

import static ru.sbt.mipt.oop.sensors.SensorEventType.DOOR_CLOSED;
import static ru.sbt.mipt.oop.sensors.SensorEventType.DOOR_OPEN;

public class HallDoorSensorEventHandler implements SensorEventHandler {
    private final SmartHome smartHome;

    public HallDoorSensorEventHandler(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (event.getType() != DOOR_OPEN && event.getType() != DOOR_CLOSED) {
            return;
        }

        // событие от двери
        for (Room room : smartHome.getRooms()) {
            if (!room.getName().equals("hall")) {
                continue;
            }
            for (Door door : room.getDoors()) {
                if (door.getId().equals(event.getObjectId())) {
                    if (event.getType() == DOOR_CLOSED) {
                        handleHallDoorClose(room, door);
                    }
                }
            }
        }
    }

    private void handleHallDoorClose(Room room, Door door) {
        // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
        // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
        for (Room homeRoom : smartHome.getRooms()) {
            for (Light light : homeRoom.getLights()) {
                light.setOn(false);
                SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
                sendCommand(command);
            }
        }
    }

    private void sendCommand(SensorCommand command) {
        System.out.println("Pretent we're sending command " + command);
    }
}
