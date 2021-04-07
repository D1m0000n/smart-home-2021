package ru.sbt.mipt.oop.remoteControllers;

import rc.RemoteControl;
import ru.sbt.mipt.oop.remoteControllers.commands.Command;

import java.util.HashMap;
import java.util.Map;

public class RemoteController implements RemoteControl {
    private Map<String, Command> codeToCommand = new HashMap<>();

    @Override
    public void onButtonPressed(String buttonCode, String rcId) {
        // here we should get remote controller instance by rcId
        // because we dont have library remote control register let suggest
        // that we implement only one remote
        Command command = codeToCommand.get(buttonCode);
        command.doCommand();
    }

    public void bindButton(String code, Command command) {
        codeToCommand.put(code, command);
    }
}
