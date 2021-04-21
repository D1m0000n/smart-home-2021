package ru.sbt.mipt.oop.remoteControllers.commands;

import ru.sbt.mipt.oop.SmartHome;

public class ActivateAlarmCommand implements Command {
    private final SmartHome smartHome;
    private final String defaultActivationCode = "123";

    public ActivateAlarmCommand(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void doCommand() {
        smartHome.getState().activate(defaultActivationCode);
    }
}
