package com.example.lasertag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayersListAdapter extends ArrayAdapter<PlayersList> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<PlayersList> playersList;
    private ArrayList<PlayerSettingsList> playerSettingsLists;


    PlayersListAdapter(Context context, int resource, ArrayList<PlayersList> players, ArrayList<PlayerSettingsList>playerSettingsLists){
        super(context, resource, players);
        this.playersList = players;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.playerSettingsLists = playerSettingsLists;
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
            viewHolder.idMiles.setText(settings.getIdMiles());
            viewHolder.idArmax.setText(settings.getIdArmax());
            //здесь должна выводиться вся инфа об игроке
        });

        return convertView;
    }

    class ViewHolder{
        final Button deviceId;

        final TextView idMiles;
        final TextView idArmax;

        ViewHolder(View view){
            deviceId = (Button) view.findViewById(R.id.deviceId);

            idMiles = view.findViewById(R.id.textIteratorMiles);
            idArmax = view.findViewById(R.id.textIteratorArmax);
        }
    }
}
