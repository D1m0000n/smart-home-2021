package ru.sbt.mipt.oop.adapters;

import com.coolcompany.smarthome.events.CCSensorEvent;
import com.coolcompany.smarthome.events.EventHandler;
import ru.sbt.mipt.oop.EventProcessor;
import ru.sbt.mipt.oop.sensors.SensorEvent;
import ru.sbt.mipt.oop.sensors.SensorEventType;

import java.util.Map;

public class SmartHomeAdapter implements EventHandler {
    private final EventProcessor eventProcessor;
    private final Map<String, SensorEventType> transformEvents;

    public SmartHomeAdapter(EventProcessor eventProcessor, Map<String, SensorEventType> transformEvents) {
        this.eventProcessor = eventProcessor;
        this.transformEvents = transformEvents;
    }

    @Override
    public void handleEvent(CCSensorEvent event) {
        eventProcessor.processEvent(transfromCCSensorEventToSensorEvent(event));
    }

    private SensorEvent transfromCCSensorEventToSensorEvent(CCSensorEvent event) {
        SensorEventType eventType = transformCCSensorEventTypeToSensorEventType(event.getEventType());
        return new SensorEvent(eventType, event.getObjectId());
    }

    private SensorEventType transformCCSensorEventTypeToSensorEventType(String CCEventType) {
        return transformEvents.get(CCEventType);
    }
}
