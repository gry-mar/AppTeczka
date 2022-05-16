package edu.ib.appteczkaandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class PlannerCustomAdapter extends BaseAdapter implements ListAdapter{
        private ArrayList<String> listNames = new ArrayList<>();
        private ArrayList<String> listTimes = new ArrayList<>();
        private final Context context;



        public PlannerCustomAdapter(ArrayList<String> listNames, ArrayList<String> listTimes,
                                    Context context) {
            this.listNames = listNames;
            this.listTimes = listTimes;
            this.context = context;
        }


        @Override
        public int getCount() {
            return listNames.size();
        }

        @Override
        public Object getItem(int pos) {
            return listNames.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return 0;//list.get(pos).getId();
            //just return 0 if your list items do not have an Id variable.
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.custom_listview, null);
            }
            ToggleButton callbtn= view.findViewById(R.id.btn);

            //Handle TextView and display string from your list
            TextView drugName = (TextView)view.findViewById(R.id.drugName);
            drugName.setText(listNames.get(position));
            TextView drugTime = (TextView)view.findViewById(R.id.drugTime);
            drugTime.setText(listTimes.get(position));

            return view;
        }



    }
