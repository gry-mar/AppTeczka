package edu.ib.appteczkaandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlannerCustomAdapter extends BaseAdapter implements ListAdapter {
    private final Context context;
    private ArrayList<CustomListElement> customElements;
    private String emailUser;

    private FirebaseAuth mAuth;

    public PlannerCustomAdapter(ArrayList<CustomListElement> customElements,
                                Context context) {
        this.customElements = customElements;
        this.context = context;
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
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        emailUser = currentUser.getEmail();

        System.out.println("gówno" + emailUser);

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_listview, null);
        }

        TextView drugName = (TextView) view.findViewById(R.id.name);
        TextView drugTime = (TextView) view.findViewById(R.id.drugTime);
        drugName.setText(customElements.get(position).getName());
        drugTime.setText(customElements.get(position).getTime());
        ToggleButton btn = view.findViewById(R.id.btn);
        btn.setChecked(customElements.get(position).isChecked());
        btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> data = new HashMap<>();
                data.put(String.valueOf(position), b);
                System.out.println("POZYCJA PRZY ADAPTERZE: " + position);
                //btn.setChecked(b);
                System.out.println("W adapterze przy kliknięciu: " + data.toString());
                db.collection(String.valueOf(emailUser)).document("togglebuttons")
                        .set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())

                            System.out.println("Co idzie do togglebuttons: " + data);
                        Intent intent = new Intent("custom-message");
                        intent.putExtra("switchPositions", position);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                    }
                });
            }
        });



        return view;
    }

}
