package app.apphub.devon.walkingquest.GridAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.apphub.devon.walkingquest.R;
import app.apphub.devon.walkingquest.database.objects.Item;

/**
 * Created by Devon on 4/3/2017.
 */

public class InventorySelectorGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Item> items;
    private LayoutInflater layoutInflater;

    public InventorySelectorGridAdapter(Context context, ArrayList<Item> items){
        this.context = context;
        this.items = items;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {


        if(view == null){
            view = layoutInflater.inflate(R.layout.selector_grid_cell, null);
        }

        TextView tv = (TextView) view.findViewById(R.id.grid_item);
        tv.setText(items.get(position).toString());

        return view;

    }
}
