import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.handlers.LightSensorEventHandler;
import ru.sbt.mipt.oop.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.readers.JSONSmartHomeReader;
import ru.sbt.mipt.oop.readers.SmartHomeReader;
import ru.sbt.mipt.oop.sensors.SensorEvent;
import ru.sbt.mipt.oop.sensors.SensorEventType;

import java.io.File;

public class LightWorkTest {

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

    private static SmartHome smartHome;

    @BeforeAll
    static void init() {
        String filename = "./src/test/resources/smart-home-test.js";
        SmartHomeReader reader = new JSONSmartHomeReader(filename);
        smartHome = reader.readSmartHome();
    }

    @Test
    public void lightOffWork() {
        LightFinder lightFinder = new LightFinder("1");
        smartHome.doAction(lightFinder);
        Light light = lightFinder.getFoundLight();
        assertFalse(light.isOn());
    }

    @Test
    public void lightOnWork() {
        LightFinder lightFinder = new LightFinder("3");
        smartHome.doAction(lightFinder);
        Light light = lightFinder.getFoundLight();
        assertTrue(light.isOn());
    }

    @Test
    public void lightSwitchWork() {
        SensorEvent lightSwitch = new SensorEvent(SensorEventType.LIGHT_OFF, "3");
        SensorEventHandler eventHandler = new LightSensorEventHandler(smartHome, lightSwitch);
        eventHandler.handleEvent();

        LightFinder lightFinder = new LightFinder("3");
        smartHome.doAction(lightFinder);
        Light light = lightFinder.getFoundLight();

        assertFalse(light.isOn());
    }
}
