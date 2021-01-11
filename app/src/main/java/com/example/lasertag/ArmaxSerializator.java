package com.example.lasertag;

import com.example.lasertag.playerPackageTypes.ArmaxPlayerImHere;
import com.example.lasertag.playerPackageTypes.ArmaxRadioKill;
import com.example.lasertag.playerPackageTypes.ArmaxRadioRespawn;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ArmaxSerializator {
    public ArmaxHeader readHeader(ByteBuffer data) {
        ArmaxHeader armaxHeader = new ArmaxHeader();

        data.order(ByteOrder.BIG_ENDIAN);

        armaxHeader.setAddressId(data.getInt());
        armaxHeader.setDeviceId(data.getInt());
        byte retranslateField = data.get();
        armaxHeader.setRetranslate(retranslateField >> 7 == -1); //проверить
        armaxHeader.setPackageNumber((byte) (retranslateField & 0b01111111));
        armaxHeader.setDeviceGroup(data.get());
        armaxHeader.setPackageType(data.get());

        return armaxHeader;
    }

    public byte[] writeHeader(ArmaxHeader armaxHeader){
        ByteBuffer allocate = ByteBuffer.allocate(11);
        allocate.order(ByteOrder.BIG_ENDIAN);

        allocate.putInt(armaxHeader.getAddressId());
        allocate.putInt(armaxHeader.getDeviceId());
        byte retranslateField = armaxHeader.isRetranslate() ?(byte) (1<<7) :0 ;
        retranslateField = (byte) (retranslateField | armaxHeader.getPackageNumber());
        allocate.put(retranslateField);
        allocate.put(armaxHeader.getDeviceGroup());
        allocate.put(armaxHeader.getPackageType());

        return allocate.array();
    }

    public ArmaxRadioKill readRadioKill (ByteBuffer data){
        ArmaxRadioKill armaxRadioKill = new ArmaxRadioKill();
        data.order(ByteOrder.BIG_ENDIAN);

        armaxRadioKill.setGroupColor(data.get());

        return armaxRadioKill;
    }

    public byte[] writeRadioKill (ArmaxRadioKill armaxRadioKill){
        ByteBuffer allocate = ByteBuffer.allocate(34);
        allocate.order(ByteOrder.BIG_ENDIAN);
        allocate.put(armaxRadioKill.getGroupColor());

        return allocate.array();
    }

    public ArmaxRadioRespawn readRadioRespawn (ByteBuffer data){
        ArmaxRadioRespawn armaxRadioRespawn = new ArmaxRadioRespawn();
        data.order(ByteOrder.BIG_ENDIAN);

        armaxRadioRespawn.setGroupColor(data.get());

        return armaxRadioRespawn;
    }

    public byte[] writeRadioRespawn (ArmaxRadioRespawn armaxRadioRespawn){
        ByteBuffer allocate = ByteBuffer.allocate(34);
        allocate.order(ByteOrder.BIG_ENDIAN);
        allocate.put(armaxRadioRespawn.getGroupColor());

        return allocate.array();
    }

    public ArmaxPlayerImHere readPlayerImHere (ByteBuffer data){
        ArmaxPlayerImHere armaxPlayerImHere = new ArmaxPlayerImHere();
        data.order(ByteOrder.BIG_ENDIAN);
        armaxPlayerImHere.setDeviceState(data.get());
        armaxPlayerImHere.setIrProtocol(data.get());
        armaxPlayerImHere.setTeam(data.get());
        armaxPlayerImHere.setIdMiles(data.get());
        armaxPlayerImHere.setIdArmax(data.get());
        armaxPlayerImHere.setLives(data.get());
        armaxPlayerImHere.setCurrentHealth(data.get());
        armaxPlayerImHere.setMaxHealth(data.get());
        armaxPlayerImHere.setGameSession(data.get());
        armaxPlayerImHere.setVolumeLevel(data.get());
        armaxPlayerImHere.setAutoReload(data.get());
        armaxPlayerImHere.setBackgroundLight(data.get());
        armaxPlayerImHere.setBatteryLevel(data.get());

        return armaxPlayerImHere;
    }

    public byte[] writePlayerImHere(ArmaxPlayerImHere armaxPlayerImHere){
        ByteBuffer allocate = ByteBuffer.allocate(34);
        allocate.order(ByteOrder.BIG_ENDIAN);

        allocate.put(armaxPlayerImHere.getDeviceState());
        allocate.put(armaxPlayerImHere.getIrProtocol());
        allocate.put(armaxPlayerImHere.getTeam());
        allocate.put(armaxPlayerImHere.getIdMiles());
        allocate.put(armaxPlayerImHere.getIdArmax());
        allocate.put(armaxPlayerImHere.getLives());
        allocate.putShort(armaxPlayerImHere.getCurrentHealth());
        allocate.putShort(armaxPlayerImHere.getMaxHealth());
        allocate.put(armaxPlayerImHere.getGameSession());
        allocate.put(armaxPlayerImHere.getVolumeLevel());
        allocate.put(armaxPlayerImHere.getAutoReload());
        allocate.put(armaxPlayerImHere.getBackgroundLight());
        allocate.put(armaxPlayerImHere.getBatteryLevel());

        return allocate.array();
    }
}
