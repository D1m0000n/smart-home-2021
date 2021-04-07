package ru.sbt.mipt.oop;

import java.util.Collection;

public class Room implements Actionable, HomeComponent {
    private Collection<Light> lights;
    private Collection<Door> doors;
    private String name;

    public Room(Collection<Light> lights, Collection<Door> doors, String name) {
        this.lights = lights;
        this.doors = doors;
        this.name = name;
    }

    public Collection<Light> getLights() {
        return lights;
    }

    public Collection<Door> getDoors() {
        return doors;
    }

    public String getName() {
        return name;
    }

    @Override
    public void doAction(Action action) {
        action.doAction(this);
        lights.forEach(light -> light.doAction(action));
        doors.forEach(door -> door.doAction(action));
    }
}
