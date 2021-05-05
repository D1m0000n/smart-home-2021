package ru.sbt.mipt.oop;

public class EventProcessor {
    public final SmartHome smartHome;
    private final List<SensorEventHandler> handlers;

    public EventProcessor(SmartHome smartHome, List<SensorEventHandler> handlers) {
        this.smartHome = smartHome;
        this.handlers = handlers;
    }

    public void processEvent(SensorEvent event) {
        System.out.println("Got event: " + event);
    }
}
