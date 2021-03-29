package ru.sbt.mipt.oop.configurations;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.EventProcessor;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.adapters.SmartHomeAdapter;
import ru.sbt.mipt.oop.readers.JSONSmartHomeReader;
import ru.sbt.mipt.oop.readers.SmartHomeReader;

@Configuration
public class SmartHomeConfiguration {

    @Bean
    SmartHomeReader smartHomeReader() {
        String filename = "smart-home-1.js";
        return new JSONSmartHomeReader(filename);
    }

    @Bean
    SmartHome smartHome() {
        return smartHomeReader().readSmartHome();
    }

    @Bean
    EventProcessor eventProcessor() {
        return new EventProcessor(smartHome());
    }

    @Bean
    SmartHomeAdapter adapter() {
        return new SmartHomeAdapter(eventProcessor());
    }

    @Bean
    SensorEventsManager adapterToSensorEventsManager() {
        SensorEventsManager eventsManager = new SensorEventsManager();
        eventsManager.registerEventHandler(adapter());
        return eventsManager;
    }
}
