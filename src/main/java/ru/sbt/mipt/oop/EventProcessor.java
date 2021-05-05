package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.handlers.*;
import ru.sbt.mipt.oop.sensors.SensorEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.sbt.mipt.oop.sensors.SensorEventType.*;

public class EventProcessor {
    public final SmartHome smartHome;
    private final List<SensorEventHandler> handlers;

    public EventProcessor(SmartHome smartHome, List<SensorEventHandler> handlers) {
        this.smartHome = smartHome;
        this.handlers = handlers;
    }

    public void processEvent(SensorEvent event) {
        System.out.println("Got event: " + event);

        handlers.forEach(handler -> handler.handleEvent(event));
    }
}
