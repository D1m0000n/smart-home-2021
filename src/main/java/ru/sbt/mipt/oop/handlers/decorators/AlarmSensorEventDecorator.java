package ru.sbt.mipt.oop.handlers.decorators;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.handlers.GeneralSensorEventHandler;
import ru.sbt.mipt.oop.senders.SMSMessageSender;
import ru.sbt.mipt.oop.sensors.SensorEvent;
import ru.sbt.mipt.oop.sensors.alarm.Alarm;
import ru.sbt.mipt.oop.sensors.alarm.AlarmSensorEvent;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmState;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmStateDeactivated;

import static ru.sbt.mipt.oop.sensors.SensorEventType.ALARM_ACTIVATE;
import static ru.sbt.mipt.oop.sensors.SensorEventType.ALARM_DEACTIVATE;

public class AlarmSensorEventDecorator extends SensorEventHandlerDecorator {
//    private final SensorEvent event;
    private final SmartHome smartHome;
    private final Alarm alarm;
    private boolean isAlarmAction;

    public AlarmSensorEventDecorator(GeneralSensorEventHandler eventHandler) {
        super(eventHandler);
//        this.event = eventHandler.getEvent();
        this.smartHome = eventHandler.getSmartHome();
        this.alarm = new Alarm(smartHome);
        this.isAlarmAction = false;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        beforeHandleEvent(event);
        boolean ignoreEvent = alarm.ignoreEvent();
        if (!isAlarmAction) {
            alarm.trigger();
            if (!ignoreEvent) { // важно, что сигнализация должна выполнить действие(если включена),
                wrappedEventHandler.handleEvent(event); // и только потом включить режим тревоги
            }
        }
    }

    @Override
    protected void beforeHandleEvent(SensorEvent event) {
        activateDeactivate(event);
    }

    private void activateDeactivate(SensorEvent event) {
        if (event.getType() == ALARM_ACTIVATE) {
            AlarmSensorEvent alarmEvent = (AlarmSensorEvent) event;
            alarm.activate(alarmEvent.getAlarmCode());
            isAlarmAction = true;
        }
        if (event.getType() == ALARM_DEACTIVATE) {
            AlarmSensorEvent alarmEvent = (AlarmSensorEvent) event;
            alarm.deactivate(alarmEvent.getAlarmCode());
            isAlarmAction = true;
        }
    }
}
