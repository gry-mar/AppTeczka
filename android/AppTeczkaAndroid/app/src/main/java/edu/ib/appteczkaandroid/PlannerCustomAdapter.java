package edu.ib.appteczkaandroid;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.Arrays;

public class PlannerCustomAdapter extends BaseAdapter implements ListAdapter {
    private final Context context;
    private ArrayList<CustomListElement> customElements;

    public PlannerCustomAdapter(ArrayList<CustomListElement> customElements,
                                Context context) {
        this.customElements = customElements;
        this.context = context;
    }


    public ArrayList<CustomListElement> getList() {
        System.out.println(Arrays.asList(customElements));
        return customElements;
    }

    @Override
    public int getCount() {
        return customElements.size();
    }

    @Override
    public Object getItem(int pos) {
        return customElements.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;//list.get(pos).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_listview, null);
        }
        ToggleButton callbtn = view.findViewById(R.id.btn);

        TextView drugName = (TextView) view.findViewById(R.id.drugName);
        drugName.setText(customElements.get(position).getDrugName());
        TextView drugTime = (TextView) view.findViewById(R.id.drugTime);
        drugTime.setText(customElements.get(position).getTime());


        callbtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i("test", isChecked + " " + position);
            customElements.get(position).setChecked(isChecked);

            Intent intent = new Intent("custom-message");
            //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
            intent.putExtra("switchBooleans", customElements.get(position).isChecked());
            intent.putExtra("switchPositions", position);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        });

        return view;
    }

}
