package com.example.lasertag;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lasertag.playerPackageTypes.ArmaxPlayerImHere;
import com.example.lasertag.playerPackageTypes.ArmaxRadioKill;
import com.example.lasertag.playerPackageTypes.ArmaxRadioRespawn;
import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RecievingRadioPackage extends AppCompatActivity{
    public final String ACTION_USB_PERMISSION = "com.hariharan.arduinousb.USB_PERMISSION";
    public final String android_id = "1234567891";
    public UsbDeviceConnection mConnection;
    public UsbDevice device;
    public UsbSerialDevice serialPort;

    MessageSender messageSender;

    ArrayList<PlayersList> playersUi = new ArrayList<PlayersList>();
    PlayersListAdapter playersListAdapter;
    ArrayList<PlayerSettingsList> playerSettingsLists = new ArrayList<>();
//    PlayerSettingsListAdapter playerSettingsListAdapter;
    ArrayList playersAndSettings = new ArrayList();

    HashMap<Integer, Integer> players;

    ListView listPlayers;
    ScrollView playerSettingsView;

    public byte[] radioPackage = new byte[15];
    public byte[] radioPackageWrite = new byte[15];
    public byte[] radioDataWrite = new byte[34];
    public byte[] newRadioPackageWrite = new byte[45];

    public int packageNumberWrite = 0;
    TextView textView;
    UsbManager mUsbManager;
    EditText power, channel;
    Button savePower, saveChannel;
    long unixTime = 0;

    byte powerByte = 48;
    byte[] PowerByte = new byte[]{
            (byte)0x50, (byte) powerByte
    };

    public int deviceId;
    public int deviceIdByte;
    public int addressId;
    private boolean retranslate;
    private byte packageNumber;
    private byte deviceGroup;
    private byte packageType;

    private byte deviceState;
    private byte irProtocol;
    private byte team;
    private byte idMiles;
    private byte idArmax;
    private byte lives;
    private short currentHealth;
    private short maxHealth;
    private byte gameSession;
    private byte volumeLevel;
    private byte autoReload;
    private byte backgroundLight;
    private byte batteryLevel;


    TextView idMilesView;
    TextView idArmaxView;
//    String irProtocolView;
//    String teamView;
//    String gameSessionView;
//    String backgroundLightView;
//    String autoReloadView;
//    String volumeLevelView;

    private Consumer<String> testPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_settings);
        textView = findViewById(R.id.tvAppendText);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        registerReceiver(broadcastReceiver, filter);

        mUsbManager = (UsbManager) getSystemService(USB_SERVICE);

        power = findViewById(R.id.Power);
        channel = findViewById(R.id.Channel);
        savePower = findViewById(R.id.SavePower);
        saveChannel = findViewById(R.id.SaveChannel);

        idArmaxView = findViewById(R.id.textIteratorArmax);
        idMilesView = findViewById(R.id.textIteratorMiles);

        listPlayers = findViewById(R.id.listPlayers);
        playerSettingsView = findViewById(R.id.playerSettingsView);
        players = new HashMap<>();

        playersListAdapter = new PlayersListAdapter(this, R.layout.list_player,
                R.layout.player_settings, playersUi, playerSettingsLists, testPlayer -> idMilesView.setText("111"));
        listPlayers.setAdapter(playersListAdapter);
//        playerSettingsListAdapter = new PlayerSettingsListAdapter(this, R.layout.list_player_settings, playerSettingsLists);
//        playerSettingsView.setAdapter(playerSettingsListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startConnection();
    }


    @SuppressLint("SetTextI18n")
    public void startConnection() {
        HashMap<String, UsbDevice> usbDevices = mUsbManager.getDeviceList();
        if (!usbDevices.isEmpty()) {
            boolean keep = true;
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                device = entry.getValue();
                int deviceVID = device.getDeviceId();
                if (deviceVID == 0x3EA) {
                    PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
                    mUsbManager.requestPermission(device, pi);

                    keep = false;
                } else {
                    mConnection = null;
                    device = null;
                }
                if (!keep)
                    break;
            }
        }
    }

    public void tvAppend(CharSequence text) {
        final TextView ftv = textView;
        final CharSequence ftext = text;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ftv.append(ftext);
            }
        });
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_USB_PERMISSION)) {
                boolean granted = intent.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
                if (granted) {
                    mConnection = mUsbManager.openDevice(device);
                    serialPort = UsbSerialDevice.createUsbSerialDevice(device, mConnection);
                    messageSender = new MessageSender(serialPort);
                    if (serialPort != null) {
                        if (serialPort.open()) {
//                            startPower();
                            serialPort.write(PowerByte);
                            serialPort.setBaudRate(115200);
                            serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                            serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                            serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                            serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                            serialPort.read(mCallback);}
                        else
                        {
                            Log.d("SERIAL", "PORT NOT OPEN");}
                    }
                    else
                    {Log.d("SERIAL", "PORT IN NULL");}
                }
                else
                {Log.d("SERIAL", "PERM NOT GRANTED");}
            }
            else  if (intent.getAction().equals(UsbManager.ACTION_USB_ACCESSORY_ATTACHED))
            {startConnection();}
        }
    };

    UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() {
        @Override
        public void onReceivedData(byte[] inputData) {

            ByteBuffer byteBuffer = ByteBuffer.wrap(inputData);

            byte identificator = byteBuffer.get();


            if(identificator == 0x44) //D
            {
                try {
                    checkDataPackage(byteBuffer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if(identificator == 78) //N
            {
                tvAppend("NEED TO RECONNECT USB");
//                flag_answer = 2;
            }
            else if(identificator == 89) //Y
            {
                unixTime = System.currentTimeMillis();
//                tvAppend("Готово\n" + unixTime + "\n");

                messageSender.ackSendMessage();
            }
            else {
                messageSender.ackSendMessage();
                tvAppend("Unknown identificator");
                try {
                    checkDataPackage(byteBuffer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void checkDataPackage(ByteBuffer byteBuffer) throws InterruptedException {


        ArmaxSerializator armaxSerializator = new ArmaxSerializator();
        ArmaxHeader armaxHeader = armaxSerializator.readHeader(byteBuffer);

        int android_ID = (int) (Integer.parseInt(android_id));

        if (armaxHeader.getAddressId() == 0 || armaxHeader.getAddressId() == android_ID) {
            if (armaxHeader.getPackageType() == 17) {
                tvAppend("new Player!");

                ArmaxPlayerImHere armaxPlayerImHere = armaxSerializator.readPlayerImHere(byteBuffer);

//                queue = (armaxSerializator.writeHeader(armaxHeader));
                tvAppend(String.valueOf(armaxHeader.getDeviceId()));
                deviceId = armaxHeader.getDeviceId();
                deviceIdByte = armaxHeader.getDeviceId();

                addressId = armaxHeader.getAddressId();
                packageNumber = armaxHeader.getPackageNumber();
                deviceGroup = armaxHeader.getDeviceGroup();
                packageType = armaxHeader.getPackageType();

                deviceState = armaxPlayerImHere.getDeviceState();
                irProtocol = armaxPlayerImHere.getIrProtocol();
                team = armaxPlayerImHere.getTeam();
                idMiles = armaxPlayerImHere.getIdMiles();
                idArmax = armaxPlayerImHere.getIdMiles();
                lives = armaxPlayerImHere.getLives();
                currentHealth = armaxPlayerImHere.getCurrentHealth();
                maxHealth = armaxPlayerImHere.getMaxHealth();
                gameSession = armaxPlayerImHere.getGameSession();
                volumeLevel = armaxPlayerImHere.getVolumeLevel();
                autoReload = armaxPlayerImHere.getAutoReload();
                backgroundLight = armaxPlayerImHere.getBackgroundLight();
                batteryLevel = armaxPlayerImHere.getBatteryLevel();

//                playerBuffer.order(ByteOrder.BIG_ENDIAN);
////                playerBuffer.putInt(armaxHeader.getDeviceId());
//                ArmaxPlayerImHere armaxPlayerImHere = armaxSerializator.readPlayerImHere(byteBuffer);
//                tvAppend(String.valueOf(armaxHeader.getAddressId()));
//                tvAppend(Arrays.toString(armaxSerializator.writeHeader(armaxHeader)));
//                tvAppend(String.valueOf(armaxHeader.getPackageNumber()));
//                tvAppend(String.valueOf(armaxHeader.getDeviceGroup()));
//                tvAppend(String.valueOf(armaxHeader.getPackageType()).concat("\n"));
            }
            tvAppend("\n");
            if (armaxHeader.getPackageType() == 13) {
                ArmaxRadioKill armaxRadioKill = armaxSerializator.readRadioKill(byteBuffer);

                tvAppend(String.valueOf(armaxHeader.getAddressId()));
                tvAppend(String.valueOf(armaxHeader.getDeviceId()));
                tvAppend(String.valueOf(armaxHeader.getPackageNumber()));
                tvAppend(String.valueOf(armaxHeader.getDeviceGroup()).concat("\n"));
                tvAppend(String.valueOf(armaxHeader.getPackageType()).concat("\n"));
                tvAppend(String.valueOf(armaxRadioKill.getGroupColor()));
            }
        }
    }

    public void addPlayer(View view) {

        playersUi.clear();
        players.put(deviceId, deviceIdByte);
        for (Map.Entry<Integer, Integer> player : players.entrySet()) {
//            playersUi.add(String.valueOf(player.getValue()));

            playersUi.add(new PlayersList(String.valueOf(player.getValue())));
//            testPlayer = String.valueOf(player.getValue());


            playerSettingsLists.add(new PlayerSettingsList((idMiles), (idArmax), String.valueOf(irProtocol),
                    String.valueOf(team), String.valueOf(gameSession), String.valueOf(backgroundLight),
                    String.valueOf(autoReload), String.valueOf(volumeLevel)));

            playersListAdapter.notifyDataSetChanged();

//            playerSettingsListAdapter.notifyDataSetChanged();
//            setPlayerSettingsView(String.valueOf(idMiles), String.valueOf(idArmax));
        }
    }

    private void setPlayerSettingsView(String idMiles, String idArmax){
        idMilesView.setText(idMiles);
        idArmaxView.setText(idArmax);
    }



    public void myPlayer(View view) {
        playersUi.add(new PlayersList("meow"));
        playersListAdapter.notifyDataSetChanged();
        playerSettingsLists.add(new PlayerSettingsList((1), (1), String.valueOf(1),
                String.valueOf(1), String.valueOf(1), String.valueOf(1),
                String.valueOf(1), String.valueOf(1)));
        setPlayerSettingsView(String.valueOf(1), String.valueOf(1));
    }

//    playersUi.clear();
//        playerDevice.put(deviceId, (byte) deviceId);
//        for (Map.Entry<Integer, Byte> player : playerDevice.entrySet()) {
//        playersUi.add(Arrays.toString(player.getValue()));
//        adapterUi.notifyDataSetChanged();
//    }
//        playersUi.add(playerDevice.get(deviceId).toString());

    public void onClickChannel(View v){
        if (channel.getText()==null) tvAppend("No Text");
        byte channelByte = Byte.parseByte(String.valueOf(channel.getText()));

        byte[] ChannelByte = new byte[2];
        ChannelByte[0] = 0x43;
        ChannelByte[1] = channelByte;

        serialPort.write(ChannelByte);
    }
    public void radioKill(View v) throws InterruptedException {
        ArmaxSerializator armaxSerializator = new ArmaxSerializator();
        ArmaxRadioKill armaxRadioKill = new ArmaxRadioKill();
        armaxRadioKill.setGroupColor((byte)4);
        radioDataWrite = armaxSerializator.writeRadioKill(armaxRadioKill);

        ArmaxHeader armaxHeader = new ArmaxHeader();
        armaxHeader.setAddressId((int) 0);
        armaxHeader.setDeviceId((int) 0x0F0F0F0F);
        armaxHeader.setRetranslate(false);
        armaxHeader.setPackageNumber((byte)++packageNumberWrite);
        armaxHeader.setDeviceGroup((byte) 0);
        armaxHeader.setPackageType((byte)8);
        radioPackageWrite = armaxSerializator.writeHeader(armaxHeader);

        newRadioPackageWrite[0] = (byte) 0x44;
        System.arraycopy(radioPackageWrite, 0, newRadioPackageWrite, 1, 11);
        System.arraycopy(radioDataWrite,0, newRadioPackageWrite, 12, 33);

//        tvAppend(String.valueOf((byte)0x44+";"));
//        tvAppend(Arrays.toString(newRadioPackageWrite));
//        unixTime = System.currentTimeMillis();
//        tvAppend(unixTime + "\n");
        write(newRadioPackageWrite);

    }
    public void radioRespawn(View v) throws InterruptedException {
        ArmaxSerializator armaxSerializator = new ArmaxSerializator();
        ArmaxRadioRespawn armaxRadioRespawn = new ArmaxRadioRespawn();
        armaxRadioRespawn.setGroupColor((byte)4);
        radioDataWrite = armaxSerializator.writeRadioRespawn(armaxRadioRespawn);

        ArmaxHeader armaxHeader = new ArmaxHeader();
        armaxHeader.setAddressId((int) 0);
        armaxHeader.setDeviceId((int) 0x0F0F0F0F);
        armaxHeader.setRetranslate(false);
        armaxHeader.setPackageNumber((byte)++packageNumberWrite);
        armaxHeader.setDeviceGroup((byte) 0);
        armaxHeader.setPackageType((byte)14);
        radioPackageWrite = armaxSerializator.writeHeader(armaxHeader);

        newRadioPackageWrite[0] = (byte) 0x44;
        System.arraycopy(radioPackageWrite, 0, newRadioPackageWrite, 1, 11);
        System.arraycopy(radioDataWrite,0, newRadioPackageWrite, 12, 33);

        write(newRadioPackageWrite);
    }

    public void TestData(View v) throws InterruptedException {
        byte[] ChannelByte = new byte[2];
        ChannelByte[0] = 0x44;
        ChannelByte[1] = 0x45;

        write(ChannelByte);
    }

    public void write(byte [] data) throws InterruptedException {
        textView.setText(" ");
        messageSender.sendMessage(data);
        tvAppend("WAIT");
    }

    public void clear(View view){
        textView.setText(" ");
    }

    public void kill(View v){
        serialPort.close();
        tvAppend("\nSerial Connection Closed! \n");

    }
}