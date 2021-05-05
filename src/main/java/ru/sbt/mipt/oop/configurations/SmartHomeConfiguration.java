package ru.sbt.mipt.oop.configurations;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rc.RemoteControl;
import rc.RemoteControlRegistry;
import ru.sbt.mipt.oop.EventProcessor;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.adapters.SmartHomeAdapter;
import ru.sbt.mipt.oop.readers.JSONSmartHomeReader;
import ru.sbt.mipt.oop.readers.SmartHomeReader;
import ru.sbt.mipt.oop.remoteControllers.RemoteController;
import ru.sbt.mipt.oop.remoteControllers.commands.*;
import ru.sbt.mipt.oop.sensors.SensorEventType;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, SensorEventType> transform = transformCCSensorEventTypeToSensorEventType();
        return new SmartHomeAdapter(eventProcessor(), transform);
    }

    @Bean
    Map<String, SensorEventType> transformCCSensorEventTypeToSensorEventType() {
        Map<String, SensorEventType> transform = new HashMap<>();
        transform.put("LightIsOn", SensorEventType.LIGHT_ON);
        transform.put("LightIsOff", SensorEventType.LIGHT_OFF);
        transform.put("DoorIsOpen", SensorEventType.DOOR_OPEN);
        transform.put("DoorIsUnlocked", SensorEventType.DOOR_OPEN);
        transform.put("DoorIsClosed", SensorEventType.DOOR_CLOSED);
        transform.put("DoorIsLocked", SensorEventType.DOOR_CLOSED);
        return transform;
    }

    @Bean
    SensorEventsManager adapterToSensorEventsManager() {
        SensorEventsManager eventsManager = new SensorEventsManager();
        eventsManager.registerEventHandler(adapter());
        return eventsManager;
    }

    @Bean
    RemoteControl remoteControl() {
        RemoteControl remoteControl = new RemoteController();
        // here we can bind buttons
        return remoteControl;
    }

    @Bean
    RemoteControlRegistry remoteControlRegistry() {
        RemoteControlRegistry remoteControlRegistry = new RemoteControlRegistry();
        remoteControlRegistry.registerRemoteControl(remoteControl(), "");
        return remoteControlRegistry;
    }

    @Bean
    Command activateAlarmCommand() {
        return new ActivateAlarmCommand(smartHome());
    }

    @Bean
    Command alertAlarmCommand() {
        return new AlertAlarmCommand(smartHome());
    }

    @Bean
    Command closeHallDoorCommand() {
        return new CloseHallDoorCommand(smartHome(), "4");
    }

    @Bean
    Command turnOffAllLightCommand() {
        return new TurnOffAllLightCommand(smartHome());
    }

    @Bean
    Command turnOnAllLightCommand() {
        return new TurnOnAllLightCommand(smartHome());
    }

    @Bean
    Command turnOnLightCommand() {
        return new TurnOnHallLightCommand(smartHome());
    }
}
