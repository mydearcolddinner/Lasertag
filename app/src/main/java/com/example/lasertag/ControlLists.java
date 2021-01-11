package com.example.lasertag;

import java.util.Calendar;

public class ControlLists extends Thread {

    public byte [][] PlayerList = new byte[53][50];
    public  byte [][] GunsList = new byte[53][50];
    public byte [][] DeviceList = new byte[53][50];
    byte[]RadioPCG = new byte[45];
    short DeviceGroup = 0;
    int Device_ID = 0, CheckDeviceID = 0;


    public int flag_device_group = 0, check_flag_device_group = 0;
    RecievingRadioPackage recievingRadioPackage = new RecievingRadioPackage();

    public void GetValues(){

//        for(int i=0; i<45;i++){
//            RadioPCG[i] = ConvertBytesToShort
//        }

    }
    @Override
    public void run(){
        flag_device_group = recievingRadioPackage.flag_device_group;
        RadioPCG= recievingRadioPackage.radioPackage;
        Calendar now = Calendar.getInstance();
        now.getTimeInMillis();

        if (flag_device_group != check_flag_device_group)
        {
            DeviceGroup |= RadioPCG[9];
            DeviceGroup &= 0x00FF;
            Device_ID = 0;
            for (int i = 0; i<4; i++){
                Device_ID |= RadioPCG[4+i];
            }

            switch (DeviceGroup)
            {
                case(0):
                    break;
                case(1): //повязка
                    for (int i = 0; i<100; i++){
                        CheckDeviceID = 0;
                        for (int j = 0; j<4; j++){
                            CheckDeviceID = PlayerList[j][8+4];
                        }
                        if (CheckDeviceID == Device_ID){
                            for (int j = 0; j < 45; j++){
                                PlayerList[i][j] = RadioPCG[j];}
                            for (int j = 0; j < 8; j++){

                            }
                        }

                    }
                    break;
                case(2): //оружие
                    break;
                case(3):
                    break;
                case(4):
                    break;
                case(5):
                    break;
                case(6):
                    break;
                case(7):
                    break;
                case(8):
                    break;
                case(9):
                    break;
                case(10):
                    break;
            }
        }
    }

}
