package ru.sbt.mipt.oop;

public class Light implements Actionable, HomeComponent {
    private boolean isOn;
    private final String id;

    public Light(String id, boolean isOn) {
        this.id = id;
        this.isOn = isOn;
    }

    public boolean isOn() {
        return isOn;
    }

    public String getId() {
        return id;
    }

    public void setOn(boolean on) {
        isOn = on;
    }


    @Override
    public void doAction(Action action) {
        action.doAction(this);
    }
}
