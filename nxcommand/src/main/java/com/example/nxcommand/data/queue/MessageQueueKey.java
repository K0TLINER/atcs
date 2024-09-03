package com.example.nxcommand.data.queue;


public class MessageQueueKey {
    private static final String KEY = "atcs_message_queue_id";
    private MessageQueueKey() {
    }
    public static MessageQueueKey of() {
        return new MessageQueueKey();
    }
    @Override
    public String toString() {
        return KEY;
    }
}
