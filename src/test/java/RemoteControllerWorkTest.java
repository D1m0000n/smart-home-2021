import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.handlers.GeneralSensorEventHandler;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.handlers.decorators.AlarmSensorEventDecorator;
import ru.sbt.mipt.oop.readers.JSONSmartHomeReader;
import ru.sbt.mipt.oop.readers.SmartHomeReader;
import ru.sbt.mipt.oop.remoteControllers.RemoteController;
import ru.sbt.mipt.oop.remoteControllers.commands.*;
import ru.sbt.mipt.oop.sensors.SensorEvent;
import ru.sbt.mipt.oop.sensors.SensorEventType;
import ru.sbt.mipt.oop.sensors.alarm.AlarmSensorEvent;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmStateActivated;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmStateAlert;
import ru.sbt.mipt.oop.sensors.alarm.states.AlarmStateDeactivated;

import java.util.Arrays;
import java.util.List;

//import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RemoteControllerWorkTest {

    public static class LightFinder implements Action {
        private Light foundLight;
        private final String id;

        public LightFinder(String id) {
            this.id = id;
        }

        @Override
        public void doAction(HomeComponent component) {
            if (component instanceof Light) {
                Light light = (Light) component;
                if (light.getId().equals(id)) {
                    foundLight = light;
                }
            }
        }

        public Light getFoundLight() {
            return foundLight;
        }
    }

    public static class DoorFinder implements Action {
        private Door foundDoor;
        private final String id;

        public DoorFinder(String id) {
            this.id = id;
        }

        @Override
        public void doAction(HomeComponent component) {
            if (component instanceof Door) {
                Door door = (Door) component;
                if (door.getId().equals(id)) {
                    foundDoor = door;
                }
            }
        }

        public Door getFoundDoor() {
            return foundDoor;
        }
    }

    private static SmartHome smartHome;
    private static RemoteController remoteController;
    private static final List<String> lightIds = Arrays.asList(
            "1", "2", "3", "4", "5", "6", "7", "8", "9"
    );

    @BeforeAll
    static void init() {
        String filename = "./src/test/resources/smart-home-test.js";
        SmartHomeReader reader = new JSONSmartHomeReader(filename);
        smartHome = reader.readSmartHome();

        remoteController = new RemoteController();
        remoteController.bindButton("A", new TurnOnAllLightCommand(smartHome));
        remoteController.bindButton("B", new CloseHallDoorCommand(smartHome, "4"));
        remoteController.bindButton("C", new TurnOnHallLight(smartHome));
        remoteController.bindButton("D", new ActivateAlarmCommand(smartHome));
        remoteController.bindButton("1", new AlertAlarmCommand(smartHome));
        remoteController.bindButton("2", new TurnOffAllLightCommand(smartHome));
    }

    @Test
    public void turnOnAllLightWork() {
        remoteController.onButtonPressed("A", "");

        for (String lightId : lightIds) {
            LightFinder lightFinder = new LightFinder(lightId);
            smartHome.doAction(lightFinder);
            Light light = lightFinder.getFoundLight();
            assertTrue(light.isOn());
        }
    }

    @Test
    public void closeHallDoorWork() {
        remoteController.onButtonPressed("B", "");

        for (String lightId : lightIds) {
            LightFinder lightFinder = new LightFinder(lightId);
            smartHome.doAction(lightFinder);
            Light light = lightFinder.getFoundLight();
            assertFalse(light.isOn());
        }

        DoorFinder doorFinder = new DoorFinder("4");
        smartHome.doAction(doorFinder);
        Door hallDoor = doorFinder.getFoundDoor();
        assertFalse(hallDoor.isOpen());
    }

    @Test
    public void hallLightOnWork() {
        remoteController.onButtonPressed("C", "");

        List<String> hallLightIds = Arrays.asList("7", "8", "9");

        for (String lightId : hallLightIds) {
            LightFinder lightFinder = new LightFinder(lightId);
            smartHome.doAction(lightFinder);
            Light light = lightFinder.getFoundLight();
            assertTrue(light.isOn());
        }
    }

    @Test
    public void alarmActivationWork() {
        remoteController.onButtonPressed("D", "");

        assertEquals(smartHome.getState(), new AlarmStateActivated(smartHome, "123"));
        // turn off alarm just in case
        smartHome.getState().deactivate("123");
    }

    @Test
    public void alertActivationWork() {
        remoteController.onButtonPressed("1", "");

        assertEquals(smartHome.getState(), new AlarmStateAlert(smartHome, "123"));
        // turn off alarm just in case
        smartHome.getState().deactivate("123");
    }

    @Test
    public void turnOffAllLightWork() {
        remoteController.onButtonPressed("2", "");

        for (String lightId : lightIds) {
            LightFinder lightFinder = new LightFinder(lightId);
            smartHome.doAction(lightFinder);
            Light light = lightFinder.getFoundLight();
            assertFalse(light.isOn());
        }
    }
}
