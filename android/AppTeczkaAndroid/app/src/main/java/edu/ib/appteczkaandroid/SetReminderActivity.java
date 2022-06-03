package edu.ib.appteczkaandroid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

public class SetReminderActivity extends AppCompatActivity {

    String spinnerSelectedItem;
    Spinner spinner;
    TimePicker timePicker;
    final Calendar calendar = Calendar.getInstance();
    String title;
    EditText edtTitle;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_arr, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        timePicker = findViewById(R.id.timeForNotification);
        timePicker.setIs24HourView(true);
        edtTitle = findViewById(R.id.edtTitle);

        createNotificationChannel();


    }

    public void btnReturnOnClick(View view) {
        Intent intent = new Intent(this, MenuAll.class);
        startActivity(intent);
        finish();
    }

    public void onSubmitNotifyClicked(View view) {
        title = edtTitle.getText().toString();
        int hour, minute;
        String am_pm;
        if (Build.VERSION.SDK_INT >= 23 ){
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
            System.out.println("A tu?");
        }
        else{
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
            System.out.println("No i chuj");
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                System.out.println(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        System.out.println("Min"+minute);

        Intent intent = new Intent(getApplicationContext(),Notify.class);
        intent.putExtra("titleExtra",title);
        intent.putExtra("messageExtra","Sprawdź czy wziąłeś lek");
        intent.putExtra("reqCode",100);
        sendBroadcast(intent);
        System.out.println("Czy tu doszło wgl");
        System.out.println(calendar.getTimeInMillis());


        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                pendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(){
        CharSequence name = "Default channel";
        String desc = "Opis jakis";
        String channelID = "default";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(channelID,name, importance);
        channel.setDescription(desc);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
}