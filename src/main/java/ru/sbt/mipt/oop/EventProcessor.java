package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.handlers.GeneralSensorEventHandler;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.handlers.decorators.AlarmSensorEventDecorator;
import ru.sbt.mipt.oop.sensors.SensorEvent;

public class EventProcessor {
    public final SmartHome smartHome;

    public EventProcessor(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    public void processEvent(SensorEvent event) {
        System.out.println("Got event: " + event);
        SensorEventHandler sensorEventHandler = new AlarmSensorEventDecorator(new GeneralSensorEventHandler(smartHome, event));
        sensorEventHandler.handleEvent();
    }
}
