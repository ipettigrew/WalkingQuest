package app.apphub.devon.walkingquest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Devon on 3/31/2017.
 */

public class GenericAdapter extends ArrayAdapter<String> {

    String[] objects;

    public GenericAdapter(Context context, int resource) {
        super(context, resource);
    }

    public GenericAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public GenericAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    public GenericAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.objects = objects;
    }

    public GenericAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    public GenericAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.dialog_mulit_reward, parent, false);
        TextView tv = (TextView) rowView.findViewById(R.id.generic_list_cell_text);

        tv.setText(objects[position]);

        return rowView;
    }
}
