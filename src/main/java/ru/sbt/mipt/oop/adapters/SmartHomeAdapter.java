package ru.sbt.mipt.oop.adapters;

import com.coolcompany.smarthome.events.CCSensorEvent;
import com.coolcompany.smarthome.events.EventHandler;
import ru.sbt.mipt.oop.EventProcessor;
import ru.sbt.mipt.oop.sensors.SensorEvent;
import ru.sbt.mipt.oop.sensors.SensorEventType;

public class SmartHomeAdapter implements EventHandler {
    private final EventProcessor eventProcessor;

    public SmartHomeAdapter(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
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
        SensorEventType eventType;
        switch (CCEventType) {
            case ("LightIsOn"):
                eventType = SensorEventType.LIGHT_ON;
                break;
            case ("LightIsOff"):
                eventType = SensorEventType.LIGHT_OFF;
                break;
            case ("DoorIsOpen"):
            case ("DoorIsUnlocked"):
                eventType = SensorEventType.DOOR_OPEN;
                break;
            case ("DoorIsClosed"):
            case ("DoorIsLocked"):
                eventType = SensorEventType.DOOR_CLOSED;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + CCEventType);
        }
        return eventType;
    }
}
