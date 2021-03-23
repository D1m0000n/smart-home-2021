package ru.sbt.mipt.oop.handlers.decorators;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.handlers.GeneralSensorEventHandler;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.sensors.SensorEvent;
import ru.sbt.mipt.oop.sensors.SensorEventType;
import ru.sbt.mipt.oop.sensors.alarm.AlarmSensorEvent;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmState;

import static ru.sbt.mipt.oop.sensors.SensorEventType.ALARM_ACTIVATE;
import static ru.sbt.mipt.oop.sensors.SensorEventType.ALARM_DEACTIVATE;

public class AlarmSensorEventDecorator extends SensorEventHandlerDecorator {
    private final SensorEvent event;
    private final SmartHome smartHome;
    private final AlarmState alarmState;
    private boolean isAlarmAction;

    public AlarmSensorEventDecorator(GeneralSensorEventHandler eventHandler) {
        super(eventHandler);
        this.event = eventHandler.getEvent();
        this.smartHome = eventHandler.getSmartHome();
        this.alarmState = smartHome.getState();
        this.isAlarmAction = false;
    }

    @Override
    public void handleEvent() {
        beforeHandleEvent();
        if (!isAlarmAction) {
            alarmState.trigger();
            wrappedEventHandler.handleEvent();
        }
    }

    @Override
    protected void beforeHandleEvent() {
        activateDeactivate();
    }

    private void activateDeactivate() {
        if (event.getType() == ALARM_ACTIVATE) {
            AlarmSensorEvent alarmEvent = (AlarmSensorEvent) event;
            alarmState.activate(alarmEvent.getAlarmCode());
            isAlarmAction = true;
        }
        if (event.getType() == ALARM_DEACTIVATE) {
            AlarmSensorEvent alarmEvent = (AlarmSensorEvent) event;
            alarmState.deactivate(alarmEvent.getAlarmCode());
            isAlarmAction = true;
        }
    }
}
