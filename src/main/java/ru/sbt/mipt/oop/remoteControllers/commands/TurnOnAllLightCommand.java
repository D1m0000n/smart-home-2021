package ru.sbt.mipt.oop.remoteControllers.commands;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.EventProcessor;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;

public class TurnOnAllLightCommand extends Command {

    public TurnOnAllLightCommand(SmartHome smartHome) {
        super(smartHome);
    }

    @Override
    public void doCommand() {
        Action turnOnAllLight = (o) -> {
            if (o instanceof Light) {
                Light light = (Light) o;
                light.setOn(true);
            }
        };

        smartHome.doAction(turnOnAllLight);
    }
}
