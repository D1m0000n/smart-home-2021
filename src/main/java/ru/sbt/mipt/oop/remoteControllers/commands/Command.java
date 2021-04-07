package ru.sbt.mipt.oop.remoteControllers.commands;

import ru.sbt.mipt.oop.EventProcessor;
import ru.sbt.mipt.oop.SmartHome;

public abstract class Command {
    protected final SmartHome smartHome;

    protected Command(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    public abstract void doCommand();
}
