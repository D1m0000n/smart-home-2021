import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.HomeComponent;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.handlers.DoorSensorEventHandler;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.readers.JSONSmartHomeReader;
import ru.sbt.mipt.oop.readers.SmartHomeReader;
import ru.sbt.mipt.oop.sensors.SensorEvent;
import ru.sbt.mipt.oop.sensors.SensorEventType;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoorWorkTest {

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

    @BeforeAll
    static void init() {
        String filename = "./src/test/resources/smart-home-test.js";
        SmartHomeReader reader = new JSONSmartHomeReader(filename);
        smartHome = reader.readSmartHome();
    }

    @Test
    public void doorClosedWork() {
        DoorFinder doorFinder = new DoorFinder("1");
        smartHome.doAction(doorFinder);
        Door door = doorFinder.getFoundDoor();

        assertFalse(door.isOpen());
    }

    @Test
    public void doorOpenedWork() {
        DoorFinder doorFinder = new DoorFinder("3");
        smartHome.doAction(doorFinder);
        Door door = doorFinder.getFoundDoor();

        assertTrue(door.isOpen());
    }

    @Test
    public void doorSwitchWork() {
        SensorEvent doorSwitch = new SensorEvent(SensorEventType.DOOR_CLOSED, "3");
        SensorEventHandler eventHandler = new DoorSensorEventHandler(smartHome, doorSwitch);
        eventHandler.handleEvent();

        DoorFinder doorFinder = new DoorFinder("3");
        smartHome.doAction(doorFinder);
        Door door = doorFinder.getFoundDoor();

        assertFalse(door.isOpen());

        // возвращаем дверь в исходное состояние
        SensorEvent doorSwitchBack = new SensorEvent(SensorEventType.DOOR_OPEN, "3");
        eventHandler = new DoorSensorEventHandler(smartHome, doorSwitchBack);
        eventHandler.handleEvent();
    }
}
