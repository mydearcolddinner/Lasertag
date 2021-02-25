package com.example.lasertag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.function.Consumer;

public class PlayersListAdapter extends ArrayAdapter<PlayersList> {
    private LayoutInflater inflater;
    private int layout;
    private int layoutSettings;
    private ArrayList<PlayersList> playersList;
    private ArrayList<PlayerSettingsList> playerSettingsLists;
    private final Consumer<String> playerCallback;


    PlayersListAdapter(Context context,
                       int resource,
                       int resourceSettings,
                       ArrayList<PlayersList> players,
                       ArrayList<PlayerSettingsList>playerSettingsLists,
                       Consumer<String> playerCallback){
        super(context, resource, players);
        this.playersList = players;
        this.layout = resource;
        this.layoutSettings = resourceSettings; //
        this.inflater = LayoutInflater.from(context);
        this.playerSettingsLists = playerSettingsLists;
        this.playerCallback = playerCallback;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final PlayersList player = playersList.get(position);

        final PlayerSettingsList settings = playerSettingsLists.get(playersList.indexOf(player));
        viewHolder.deviceId.setText(String.valueOf(player.getDeviceId()));


        viewHolder.deviceId.setOnClickListener(v -> {

            playerCallback.accept(String.valueOf(settings.getIdMiles()));
//            viewHolder.idMiles.setText("111");
//            viewHolder.testText.setText("111");
//            viewHolder.idArmax.setText(settings.getIdArmax());
            //здесь должна выводиться вся инфа об игроке
        });

        return convertView;
    }

    class ViewHolder{
        final Button deviceId;

        final TextView idMiles;
        final TextView idArmax;

        final TextView testText;
        ViewHolder(View view){
            deviceId = (Button) view.findViewById(R.id.deviceId);

            idMiles = (TextView) view.findViewById(R.id.textIteratorMiles);
            idArmax = (TextView) view.findViewById(R.id.textIteratorArmax);

            testText = (TextView) view.findViewById(R.id.textMiles2Id);
        }
    }
}
