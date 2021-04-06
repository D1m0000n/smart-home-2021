import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.handlers.GeneralSensorEventHandler;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.handlers.decorators.AlarmSensorEventDecorator;
import ru.sbt.mipt.oop.readers.JSONSmartHomeReader;
import ru.sbt.mipt.oop.readers.SmartHomeReader;
import ru.sbt.mipt.oop.senders.SMSMessageSender;
import ru.sbt.mipt.oop.sensors.SensorEvent;
import ru.sbt.mipt.oop.sensors.SensorEventType;
import ru.sbt.mipt.oop.sensors.alarm.AlarmSensorEvent;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmStateActivated;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmStateAlert;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmStateDeactivated;

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
        SensorEventHandler eventHandler = new AlarmSensorEventDecorator(new GeneralSensorEventHandler(smartHome));
        eventHandler.handleEvent(alarmActivationSensorEvent);
        // validation
        assertEquals(smartHome.getState(), new AlarmStateActivated(smartHome, "123", new SMSMessageSender()));

        // выключим сигнализацию обратно

        // setup
        SensorEvent alarmDeactivationSensorEvent = new AlarmSensorEvent(SensorEventType.ALARM_DEACTIVATE, "123");
        // execution
        eventHandler = new AlarmSensorEventDecorator(new GeneralSensorEventHandler(smartHome));
        eventHandler.handleEvent(alarmDeactivationSensorEvent);
        // validation
        assertEquals(smartHome.getState(), new AlarmStateDeactivated(smartHome, "123", new SMSMessageSender()));
    }

    @Test
    public void triggerAlarmAndIgnoreEventTest() {
        // setup
        SensorEvent alarmActivationSensorEvent = new AlarmSensorEvent(SensorEventType.ALARM_ACTIVATE, "123");
        // execution
        SensorEventHandler eventHandler = new AlarmSensorEventDecorator(new GeneralSensorEventHandler(smartHome));
        eventHandler.handleEvent(alarmActivationSensorEvent);
        // validation (включили сигнализацию)
        assertEquals(smartHome.getState(), new AlarmStateActivated(smartHome, "123", new SMSMessageSender()));

        // setup
        SensorEvent doorOpenSwitch = new SensorEvent(SensorEventType.DOOR_OPEN, "1");
        HallDoorWorkTest.DoorFinder doorFinder = new HallDoorWorkTest.DoorFinder("1");
        // execution
        eventHandler = new AlarmSensorEventDecorator(new GeneralSensorEventHandler(smartHome));
        eventHandler.handleEvent(doorOpenSwitch);
        smartHome.doAction(doorFinder);
        Door door = doorFinder.getFoundDoor();
        // validation
        // дверь открыта
        assertTrue(door.isOpen());
        // сигнализация в состоянии тревоги
        assertEquals(smartHome.getState(), new AlarmStateAlert(smartHome, "123", new SMSMessageSender()));

        // setup
        SensorEvent doorCloseSwitch = new SensorEvent(SensorEventType.DOOR_CLOSED, "1");
        // execution
        eventHandler = new AlarmSensorEventDecorator(new GeneralSensorEventHandler(smartHome));
        eventHandler.handleEvent(doorCloseSwitch);

        // validation
        // дверь все еще открыта, потому что сигнализация в состоянии тревоги
        assertTrue(door.isOpen());

        // выключим сигнализацию обратно
        // setup
        SensorEvent alarmDeactivationSensorEvent = new AlarmSensorEvent(SensorEventType.ALARM_DEACTIVATE, "123");
        // execution
        eventHandler = new AlarmSensorEventDecorator(new GeneralSensorEventHandler(smartHome));
        eventHandler.handleEvent(alarmDeactivationSensorEvent);
        // validation
        assertEquals(smartHome.getState(), new AlarmStateDeactivated(smartHome, "123", new SMSMessageSender()));
    }
}
