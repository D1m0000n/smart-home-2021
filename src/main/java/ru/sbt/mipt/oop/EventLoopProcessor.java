package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.sensors.SensorEvent;
import ru.sbt.mipt.oop.sensors.SensorEventCreator;

public class EventLoopProcessor {
    private final SmartHome smartHome;
    private final SensorEventCreator sensorEventCreator;
    private final EventProcessor eventProcessor;

    public EventLoopProcessor(SmartHome smartHome, SensorEventCreator sensorEventCreator, EventProcessor eventProcessor) {
        this.smartHome = smartHome;
        this.sensorEventCreator = sensorEventCreator;
        this.eventProcessor = eventProcessor;
    }

    public void loopEvents() {
        SensorEvent event = sensorEventCreator.getNextSensorEvent();

        while (event != null) {
            eventProcessor.processEvent(event);
            event = sensorEventCreator.getNextSensorEvent();
        }
    }
}
