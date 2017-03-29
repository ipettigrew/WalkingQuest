package app.apphub.devon.walkingquest.GridAdapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/** TODO:   Figure out what the fuck is wrong with this R bullshit
 * */
import app.apphub.devon.walkingquest.QuestDetailsActivity;
import app.apphub.devon.walkingquest.QuestSelectorGridActivity;
import app.apphub.devon.walkingquest.R;
import app.apphub.devon.walkingquest.database.objects.Quest;

/**
 * Created by Owner on 2017-03-01.
 */

public class QuestSelectorGridAdapter extends BaseAdapter {
    private Context context;
    private Quest[] quests;    /*  TODO:   change this into an array of Quest objects (or some other acceptable object)*/
    LayoutInflater inflater;

    public QuestSelectorGridAdapter(Context context, Quest[] quests) {
        this.context = context;
        this.quests = quests;
        inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return quests.length;
    }

    @Override
    public Quest getItem(int position) {
        return quests[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.quest_selector_grid_cell, null);
        }
        TextView t = (TextView) convertView.findViewById(R.id.grid_item);
        t.setText(quests[position].getQuestHeader());
        return convertView;
    }

}
