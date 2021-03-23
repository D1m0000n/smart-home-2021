import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.handlers.DoorSensorEventHandler;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.readers.JSONSmartHomeReader;
import ru.sbt.mipt.oop.readers.SmartHomeReader;
import ru.sbt.mipt.oop.sensors.SensorEvent;
import ru.sbt.mipt.oop.sensors.SensorEventType;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HallDoorWorkTest {

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

    public static class LightsFinder implements Action {
        private Collection<Light> foundLights;

        public LightsFinder() {
            foundLights = new ArrayList<>();
        }

        @Override
        public void doAction(HomeComponent component) {
            if (component instanceof Light) {
                Light light = (Light) component;
                foundLights.add(light);
            }
        }

        public Collection<Light> getFoundLights() {
            return foundLights;
        }
    }

    private static SmartHome smartHome;

    @BeforeAll
    static void init() {
        String filename = "./src/test/resources/smart-home-test.js";
        SmartHomeReader reader = new JSONSmartHomeReader(filename);
        smartHome = reader.readSmartHome();
    }

    @Test
    public void doorClosedWork() {

    }

    @Test
    public void doorOpenedWork() {

    }

    @Test
    public void doorSwitchWork() {
        SensorEvent hallDoorOpenEvent = new SensorEvent(SensorEventType.DOOR_OPEN, "4");
        SensorEventHandler eventHandler = new DoorSensorEventHandler(smartHome, hallDoorOpenEvent);
        eventHandler.handleEvent();

        DoorFinder doorFinder = new DoorFinder("4");
        smartHome.doAction(doorFinder);
        Door hallDoor = doorFinder.getFoundDoor();

        // проверяем, открыли ли дверь
        assertTrue(hallDoor.isOpen());

        SensorEvent hallDoorCloseEvent = new SensorEvent(SensorEventType.DOOR_CLOSED, "4");
        eventHandler = new DoorSensorEventHandler(smartHome, hallDoorCloseEvent);
        eventHandler.handleEvent();

        // проверяем, закрыли ли дверь
        assertFalse(hallDoor.isOpen());

        LightsFinder lightsFinder = new LightsFinder();
        Collection<Light> lights = lightsFinder.getFoundLights();

        // проверяем, выключился ли везде свет
        lights.forEach(light -> assertFalse(light.isOn()));
    }
}
