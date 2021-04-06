package ru.sbt.mipt.oop.handlers.decorators;

import ru.sbt.mipt.oop.handlers.GeneralSensorEventHandler;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.sensors.SensorEvent;

public class SensorEventHandlerDecorator implements SensorEventHandler {
    protected GeneralSensorEventHandler wrappedEventHandler;

    public SensorEventHandlerDecorator(GeneralSensorEventHandler eventHandler) {
        this.wrappedEventHandler = eventHandler;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        wrappedEventHandler.handleEvent(event);
    }

    protected void beforeHandleEvent(SensorEvent event) {}

    protected void afterHandleEvent() {}
}
