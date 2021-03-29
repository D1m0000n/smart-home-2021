package ru.sbt.mipt.oop.senders;

public class SMSMessageSender implements MessageSender {
    @Override
    public void sendMessage() {
        System.out.println("Sending sms");
    }
}
