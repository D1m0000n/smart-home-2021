import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.handlers.*;
import ru.sbt.mipt.oop.handlers.decorators.AlarmSensorEventDecorator;
import ru.sbt.mipt.oop.readers.JSONSmartHomeReader;
import ru.sbt.mipt.oop.readers.SmartHomeReader;
import ru.sbt.mipt.oop.senders.SMSMessageSender;
import ru.sbt.mipt.oop.sensors.SensorEvent;
import ru.sbt.mipt.oop.sensors.SensorEventType;
import ru.sbt.mipt.oop.sensors.alarm.Alarm;
import ru.sbt.mipt.oop.sensors.alarm.AlarmSensorEvent;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmState;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmStateActivated;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmStateAlert;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmStateDeactivated;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlarmWorkTest {
    private static SmartHome smartHome;

    @BeforeAll
    static void init() {
        String filename = "./src/test/resources/smart-home-test.js";
        SmartHomeReader reader = new JSONSmartHomeReader(filename);
        smartHome = reader.readSmartHome();
    }

    @Test
    public void alarmEnableWork() {
        // setup
        SensorEvent alarmActivationSensorEvent = new AlarmSensorEvent(SensorEventType.ALARM_ACTIVATE, "123");
        // execution
        AlarmSensorEventDecorator eventHandler = new AlarmSensorEventDecorator(
                smartHome,
                new GeneralSensorEventHandler(
                        smartHome,
                        Arrays.asList(
                                new LightSensorEventHandler(smartHome),
                                new DoorSensorEventHandler(smartHome),
                                new HallDoorSensorEventHandler(smartHome))));
        eventHandler.handleEvent(alarmActivationSensorEvent);
        // validation
        Alarm alarm =  eventHandler.alarm;
        assertEquals(alarm.getAlarmState(), new AlarmStateActivated(alarm, "123", new SMSMessageSender()));

        // выключим сигнализацию обратно

        // setup
        SensorEvent alarmDeactivationSensorEvent = new AlarmSensorEvent(SensorEventType.ALARM_DEACTIVATE, "123");
        // execution
        eventHandler.handleEvent(alarmDeactivationSensorEvent);
        // validation
        assertEquals(alarm.getAlarmState(), new AlarmStateDeactivated(alarm, "123", new SMSMessageSender()));
    }

    @Test
    public void triggerAlarmAndIgnoreEventTest() {
        // setup
        SensorEvent alarmActivationSensorEvent = new AlarmSensorEvent(SensorEventType.ALARM_ACTIVATE, "123");
        // execution
        AlarmSensorEventDecorator eventHandler = new AlarmSensorEventDecorator(
                smartHome,
                new GeneralSensorEventHandler(
                        smartHome,
                        Arrays.asList(
                                new LightSensorEventHandler(smartHome),
                                new DoorSensorEventHandler(smartHome),
                                new HallDoorSensorEventHandler(smartHome))));
        eventHandler.handleEvent(alarmActivationSensorEvent);
        Alarm alarm = eventHandler.alarm;

        // validation (включили сигнализацию)
        assertEquals(alarm.getAlarmState(), new AlarmStateActivated(alarm, "123", new SMSMessageSender()));

        // setup
        SensorEvent doorOpenSwitch = new SensorEvent(SensorEventType.DOOR_OPEN, "1");
        HallDoorWorkTest.DoorFinder doorFinder = new HallDoorWorkTest.DoorFinder("1");
        // execution
        eventHandler.handleEvent(doorOpenSwitch);
        smartHome.doAction(doorFinder);
        Door door = doorFinder.getFoundDoor();
        // validation
        // дверь открыта
        assertTrue(door.isOpen());
        // сигнализация в состоянии тревоги
        assertEquals(alarm.getAlarmState(), new AlarmStateAlert(alarm, "123", new SMSMessageSender()));

        // setup
        SensorEvent doorCloseSwitch = new SensorEvent(SensorEventType.DOOR_CLOSED, "1");
        // execution
        eventHandler.handleEvent(doorCloseSwitch);

        // validation
        // дверь все еще открыта, потому что сигнализация в состоянии тревоги
        assertTrue(door.isOpen());

        // выключим сигнализацию обратно
        // setup
        SensorEvent alarmDeactivationSensorEvent = new AlarmSensorEvent(SensorEventType.ALARM_DEACTIVATE, "123");
        // execution
        eventHandler.handleEvent(alarmDeactivationSensorEvent);
        // validation
        assertEquals(alarm.getAlarmState(), new AlarmStateDeactivated(alarm, "123", new SMSMessageSender()));
    }
}
