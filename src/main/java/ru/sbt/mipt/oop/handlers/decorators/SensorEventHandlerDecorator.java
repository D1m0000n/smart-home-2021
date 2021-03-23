package ru.sbt.mipt.oop.handlers.decorators;

import ru.sbt.mipt.oop.handlers.GeneralSensorEventHandler;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;

public class SensorEventHandlerDecorator implements SensorEventHandler {
    protected GeneralSensorEventHandler wrappedEventHandler;

    public SensorEventHandlerDecorator(GeneralSensorEventHandler eventHandler) {
        this.wrappedEventHandler = eventHandler;
    }

    @Override
    public void handleEvent() {
        wrappedEventHandler.handleEvent();
    }

    protected void beforeHandleEvent() {}

    protected void afterHandleEvent() {}
}
