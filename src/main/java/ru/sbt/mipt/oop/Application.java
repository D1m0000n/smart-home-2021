package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sbt.mipt.oop.adapters.SmartHomeAdapter;
import ru.sbt.mipt.oop.configurations.SmartHomeConfiguration;
import ru.sbt.mipt.oop.readers.JSONSmartHomeReader;
import ru.sbt.mipt.oop.readers.SmartHomeReader;
import ru.sbt.mipt.oop.sensors.SensorEventCreatorImpl;
import org.springframework.context.support.AbstractApplicationContext;

public class Application {

    public static void main(String... args) {
        // считываем состояние дома из файла
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(SmartHomeConfiguration.class);
        SensorEventsManager sensorEventsManager = context.getBean(SensorEventsManager.class);
        sensorEventsManager.start();

//        String filename = "smart-home-1.js";
//        SmartHomeReader reader = new JSONSmartHomeReader(filename);
//        SmartHome smartHome = reader.readSmartHome();
//
//        SmartHomeAdapter adapter = new SmartHomeAdapter(new EventProcessor(smartHome));
//        SensorEventsManager sensorEventsManager = new SensorEventsManager();
//        sensorEventsManager.registerEventHandler(adapter);
//        // начинаем цикл обработки событий
//        sensorEventsManager.start();
////        EventLoopProcessor eventLoopProcessor = new EventLoopProcessor(new SensorEventCreatorImpl(), new EventProcessor(smartHome));
//        eventLoopProcessor.loopEvents();
    }
}
