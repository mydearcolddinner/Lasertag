package com.example.lasertag;

import org.junit.Test;

public class QueueTest {
    @Test
    public void dataQueue() throws InterruptedException {
//        MessageSender messageSender = new MessageSender();
        byte[] bytes = new byte[]{
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0xF1, (byte) 0xF1, (byte) 0xF1, (byte) 0xF1,
                (byte) 0b10011000,
                (byte) 0x01,
                (byte) 0x11,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00
        };

//        messageSender.sendMessage(bytes);
    }
}
