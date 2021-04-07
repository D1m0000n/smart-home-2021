package ru.sbt.mipt.oop.remoteControllers.commands;

import ru.sbt.mipt.oop.*;

public class TurnOnHallLight extends Command {
    public TurnOnHallLight(SmartHome smartHome) {
        super(smartHome);
    }

    @Override
    public void doCommand() {
        Action hallLightOn = (o) -> {
            if (o instanceof Light) {
                Light light = (Light) o;
                light.setOn(true);
            }
        };


        Action hallDoorAction = (o) -> {
            if (o instanceof Room) {
                Room room = (Room) o;
                if (room.getName().equals("hall")) {
                    room.doAction(hallLightOn);
                }
            }
        };
        smartHome.doAction(hallDoorAction);
    }
}
