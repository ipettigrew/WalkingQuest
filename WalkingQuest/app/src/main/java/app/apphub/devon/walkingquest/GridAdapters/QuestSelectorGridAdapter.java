package app.apphub.devon.walkingquest.GridAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/** TODO:   Figure out what the fuck is wrong with this R bullshit
 * */
import java.util.ArrayList;

import app.apphub.devon.walkingquest.R;
import app.apphub.devon.walkingquest.database.objects.Quest;

/**
 * Created by Owner on 2017-03-01.
 */

public class QuestSelectorGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Quest> quests;    /*  TODO:   change this into an arraylist of Quest objects (or some other acceptable object)*/
    LayoutInflater inflater;

    public QuestSelectorGridAdapter(Context context, ArrayList<Quest> quests) {
        this.context = context;
        this.quests = quests;
        inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return quests.size();
    }

    @Override
    public Quest getItem(int position) {
        return quests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.selector_grid_cell, null);
        }
        TextView t = (TextView) convertView.findViewById(R.id.grid_item);
        t.setText(quests.get(position).getQuestHeader());
        return convertView;
    }

}
