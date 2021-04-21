package ru.sbt.mipt.oop.remoteControllers.commands;

import ru.sbt.mipt.oop.*;

public class CloseHallDoorCommand implements Command {
    private final SmartHome smartHome;
    private final String hallDoorId;

    // исключительный случай, когда без номера входной двери в конструкторе нам никак не обойтись
    public CloseHallDoorCommand(SmartHome smartHome, String hallDoorId) {
        this.smartHome = smartHome;
        this.hallDoorId = hallDoorId;
    }

    @Override
    public void doCommand() {
        Action lightOffAction = (o) -> {
            if (o instanceof Light) {
                Light light = (Light) o;
                light.setOn(false);
            }
        };

        Action checkDoorId = (o) -> {
            if (o instanceof Door) {
                Door door = (Door) o;
                if (door.getId().equals(hallDoorId)) {
                    door.setOpen(false);
                    smartHome.doAction(lightOffAction);
                }
            }
        };

        Action hallDoorAction = (o) -> {
            if (o instanceof Room) {
                Room room = (Room) o;
                if (room.getName().equals("hall")) {
                    room.doAction(checkDoorId);
                }
            }
        };
        smartHome.doAction(hallDoorAction);
    }
}
