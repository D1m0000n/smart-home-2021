package ru.sbt.mipt.oop.handlers.decorators;

import ru.sbt.mipt.oop.handlers.CompositeSensorEventHandler;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.sensors.SensorEvent;
import ru.sbt.mipt.oop.sensors.alarm.Alarm;
import ru.sbt.mipt.oop.sensors.alarm.AlarmSensorEvent;

import static ru.sbt.mipt.oop.sensors.SensorEventType.ALARM_ACTIVATE;
import static ru.sbt.mipt.oop.sensors.SensorEventType.ALARM_DEACTIVATE;

public class AlarmSensorEventDecorator implements SensorEventHandler {
    public final Alarm alarm;
    private final SensorEventHandler eventHandler;

    public AlarmSensorEventDecorator(CompositeSensorEventHandler eventHandler, Alarm alarm) {
        this.eventHandler = eventHandler;
        this.alarm = alarm;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        beforeHandleEvent(event);
        if (!isAlarmAction(event)) {
            if (!ignoreEvent(alarm)) {
                eventHandler.handleEvent(event); // и только потом включить режим тревоги
            }
            alarm.trigger();
        }
    }

    protected void beforeHandleEvent(SensorEvent event) {
        activateDeactivate(event);
    }

    private boolean isAlarmAction(SensorEvent event) {
        return event.getType() == ALARM_ACTIVATE || event.getType() == ALARM_DEACTIVATE;
    }

    private boolean ignoreEvent(Alarm alarm) {
        return alarm.isAlerted();
    }

    private void activateDeactivate(SensorEvent event) {
        if (event.getType() == ALARM_ACTIVATE) {
            AlarmSensorEvent alarmEvent = (AlarmSensorEvent) event;
            alarm.activate(alarmEvent.getAlarmCode());
        }
        if (event.getType() == ALARM_DEACTIVATE) {
            AlarmSensorEvent alarmEvent = (AlarmSensorEvent) event;
            alarm.deactivate(alarmEvent.getAlarmCode());
        }
    }
}
