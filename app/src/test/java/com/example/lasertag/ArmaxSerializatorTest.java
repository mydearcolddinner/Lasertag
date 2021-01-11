package com.example.lasertag;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArmaxSerializatorTest {
    @Test
    public void readHeader() {
        ArmaxSerializator armaxSerializator = new ArmaxSerializator();
        byte[] testData = new byte[] {
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0xF1, (byte) 0xF1, (byte) 0xF1, (byte) 0xF1,
                (byte) 0b10011000,
                (byte) 0x01,
                (byte) 0x11
        };
        ArmaxHeader armaxHeader = armaxSerializator.readHeader(ByteBuffer.wrap(testData));
        assertEquals(0, armaxHeader.getAddressId());
        assertEquals(0xF1F1F1F1, armaxHeader.getDeviceId());
        assertEquals(0b00011000, armaxHeader.getPackageNumber());
        assertEquals(0x01, armaxHeader.getDeviceGroup());
        assertEquals(0x11, armaxHeader.getPackageType());

        assertTrue(armaxHeader.isRetranslate());
    }

    @Test
    public void writeHeader(){
        ArmaxSerializator armaxSerializator = new ArmaxSerializator();

        ArmaxHeader armaxHeader = new ArmaxHeader();
        armaxHeader.setAddressId(0x00000000);
        armaxHeader.setDeviceId(0xF1F1F1F1);
        armaxHeader.setRetranslate(true);
        armaxHeader.setPackageNumber((byte) 0b00011000);
        armaxHeader.setDeviceGroup((byte) 0x01);
        armaxHeader.setPackageType((byte) 0x11);

        byte[]resultData = armaxSerializator.writeHeader(armaxHeader);
        assertEquals((byte)0x00, (byte)resultData[0]);
        assertEquals((byte)0x00, (byte)resultData[1]);
        assertEquals((byte)0x00, (byte)resultData[2]);
        assertEquals((byte)0x00, (byte)resultData[3]);
        assertEquals((byte)0xF1, (byte)resultData[4]);
        assertEquals((byte)0xF1, (byte)resultData[5]);
        assertEquals((byte)0xF1, (byte)resultData[6]);
        assertEquals((byte)0xF1, (byte)resultData[7]);
        assertEquals((byte)0b10011000, (byte)resultData[8]);
        assertEquals((byte)0x01, (byte)resultData[9]);
        assertEquals((byte)0x11, (byte)resultData[10]);
        assertTrue(armaxHeader.isRetranslate());
    }

//    @Test
//    public void readRadioKill(){
//        ArmaxSerializator armaxSerializator = new ArmaxSerializator();
//        byte groupColor = 4;
//
//        ArmaxRadioKill armaxRadioKill = armaxSerializator.readRadioKill(groupColor);
//        assertEquals((byte)4, armaxRadioKill.getGroupColor());
//    }
//
//    @Test
//    public void writeRadioKill(){
//        ArmaxSerializator armaxSerializator = new ArmaxSerializator();
//        ArmaxRadioKill armaxRadioKill = new ArmaxRadioKill();
//
//        armaxRadioKill.setGroupColor((byte) 4);
//
//        byte groupColor = armaxSerializator.writeRadioKill(armaxRadioKill);
//        assertEquals((byte)4, groupColor);
//    }

}