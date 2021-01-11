package com.example.lasertag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class PlayersListAdapter extends ArrayAdapter<PlayersList> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<PlayersList> playersList;


    PlayersListAdapter(Context context, int resource, ArrayList<PlayersList> players){
        super(context, resource, players);
        this.playersList = players;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
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

        viewHolder.deviceId.setText(String.valueOf(player.getDeviceId()));

        viewHolder.deviceId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //здесь должна выводиться вся инфа об игроке
            }
        });

        return convertView;
    }

    private class ViewHolder{
        final Button deviceId;
        ViewHolder(View view){
            deviceId = (Button) view.findViewById(R.id.deviceId);
        }
    }
}