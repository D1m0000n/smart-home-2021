package ru.sbt.mipt.oop.remoteControllers.commands;

import ru.sbt.mipt.oop.SmartHome;

public class ActivateAlarmCommand extends Command {
    private final String defaultActivationCode = "123";

    public ActivateAlarmCommand(SmartHome smartHome) {
        super(smartHome);
    }

    @Override
    public void doCommand() {
        smartHome.getState().activate(defaultActivationCode);
    }
}
