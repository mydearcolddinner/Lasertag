package com.example.lasertag;

import androidx.appcompat.app.AppCompatActivity;

import com.felhr.usbserial.UsbSerialDevice;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageSender extends AppCompatActivity {
    private BlockingQueue<byte[]> queue = new ArrayBlockingQueue<>(450);
    private byte[] dataQueue = new byte[45];
    private final Object monitor = new Object();
    private final UsbSerialDevice serialPort;

    public void sendMessage(byte[] data) throws InterruptedException {
        queue.put(data);
    }

    public MessageSender(UsbSerialDevice serialPort) {
        this.serialPort = serialPort;
        run();
    }

    private void run()  {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        dataQueue = queue.take();
                        serialPort.write(dataQueue);
                        synchronized (monitor){
                            monitor.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void ackSendMessage() {
        synchronized (monitor) {
            monitor.notify();
        }
    }
}
