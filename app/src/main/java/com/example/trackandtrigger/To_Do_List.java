package com.example.trackandtrigger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class To_Do_List extends AppCompatActivity {
    TextView Timer;
    int Hour, Min;
    EditText etDate,Title;
    String date,time,name,heading;
    int Year,Month,Day;
    private Button add;
    DatePickerDialog.OnDateSetListener setListener;
    NotificationHelper mNotificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to__do__list);
        mNotificationHelper = new NotificationHelper(this);

        Title = findViewById(R.id.Title);
        etDate = findViewById(R.id.Date);
        Calendar calendar= Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        name = getIntent().getStringExtra("name");
        add = findViewById(R.id.add);
        Timer = findViewById(R.id.Time);
        Timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(To_Do_List.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Hour=hourOfDay;
                        Min=minute;
                        time = Hour+":"+Min;
                        SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = f24Hours.parse(time);
                            SimpleDateFormat f12hours = new SimpleDateFormat("hh:mm aa");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                },12,0,false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(Hour,Min);
                timePickerDialog.show();
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        To_Do_List.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month +1;
                        Year = year;
                        Month = month;
                        Day = dayOfMonth;
                        date = Day + "/" + Month + "/" + Year;
                        etDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time_date = time + " " + date;

                heading = Title.getText().toString().trim();
                FirebaseDatabase.getInstance().getReference().child(name).child("dashboard").child("To Do List").child(heading).setValue(time_date);
                Hour--;
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY,Hour);
                c.set(Calendar.MINUTE,Min);
                c.set(Calendar.SECOND,0);
                startAlarm(c);

                onBackPressed();
            }
        });

    }

    public void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,RemainderBroadcast.class);
        intent.putExtra("Title","Remainder: "+heading);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }
}



