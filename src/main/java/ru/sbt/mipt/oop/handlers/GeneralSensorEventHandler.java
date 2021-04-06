package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.sensors.SensorEvent;

import java.util.ArrayList;
import java.util.List;

public class GeneralSensorEventHandler implements SensorEventHandler {
    private final SmartHome smartHome;
    private List<SensorEventHandler> handlers;

//    public SensorEvent getEvent() {
//        return event;
//    }

    public SmartHome getSmartHome() {
        return smartHome;
    }

    public GeneralSensorEventHandler(SmartHome smartHome) {
        this.smartHome = smartHome;
        setUpHandlers();
    }

    @Override
    public void handleEvent(SensorEvent event) {
        handlers.forEach(sensorEventHandler -> sensorEventHandler.handleEvent(event));
    }

    private void setUpHandlers() {
        handlers = new ArrayList<>();
        handlers.add(new LightSensorEventHandler(smartHome));
        handlers.add(new DoorSensorEventHandler(smartHome));
        handlers.add(new HallDoorSensorEventHandler(smartHome));
    }
}
