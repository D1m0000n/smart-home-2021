package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.sensors.alarm.states.AlarmState;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmStateDeactivated;

import java.util.ArrayList;
import java.util.Collection;

public class SmartHome implements Actionable, HomeComponent {
    Collection<Room> rooms;
    AlarmState state = new AlarmStateDeactivated(this, "");

    public SmartHome() {
        rooms = new ArrayList<>();
    }

    public SmartHome(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    @Override
    public void doAction(Action action) {
        action.doAction(this);
        rooms.forEach(room -> room.doAction(action));
    }

    public void setState(AlarmState state) {
        this.state = state;
    }

    public AlarmState getState() {
        return state;
    }
}
