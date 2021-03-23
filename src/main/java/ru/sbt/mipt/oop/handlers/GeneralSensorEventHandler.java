package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.sensors.SensorEvent;

import java.util.ArrayList;
import java.util.List;

public class GeneralSensorEventHandler implements SensorEventHandler {
    private final SmartHome smartHome;
    private final SensorEvent event;
    private List<SensorEventHandler> handlers;

    public SensorEvent getEvent() {
        return event;
    }

    public SmartHome getSmartHome() {
        return smartHome;
    }

    public GeneralSensorEventHandler(SmartHome smartHome, SensorEvent event) {
        this.smartHome = smartHome;
        this.event = event;
        setUpHandlers();
    }

    @Override
    public void handleEvent() {
        handlers.forEach(SensorEventHandler::handleEvent);
    }

    private void setUpHandlers() {
        handlers = new ArrayList<>();
        handlers.add(new LightSensorEventHandler(smartHome, event));
        handlers.add(new DoorSensorEventHandler(smartHome, event));
        handlers.add(new HallDoorSensorEventHandler(smartHome, event));
    }
}
