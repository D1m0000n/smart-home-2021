package ru.sbt.mipt.oop.remoteControllers.commands;

import ru.sbt.mipt.oop.SmartHome;

public class AlertAlarmCommand extends Command {
    private final String defaultActivationCode = "123";

    public AlertAlarmCommand(SmartHome smartHome) {
        super(smartHome);
    }

    @Override
    public void doCommand() {
        smartHome.getState().activate(defaultActivationCode);
        smartHome.getState().trigger();
    }
}
