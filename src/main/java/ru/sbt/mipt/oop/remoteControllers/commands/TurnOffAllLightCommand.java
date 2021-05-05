package ru.sbt.mipt.oop.remoteControllers.commands;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;

public class TurnOffAllLightCommand implements Command {
    private final SmartHome smartHome;

    public TurnOffAllLightCommand(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void doCommand() {
        Action turnOnAllLight = (o) -> {
            if (o instanceof Light) {
                Light light = (Light) o;
                light.setOn(false);
            }
        };

        smartHome.doAction(turnOnAllLight);
    }
}
