package ca.mcgill.ecse321.rsms_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Date;

public class SelectServiceAndCar extends AppCompatActivity {
    private ImageButton button_wash;
    private ImageButton button_maintenance;
    private ImageButton button_road_assistance;
    private ImageButton button_tire_change;
    private ImageButton button_towing;
    private ImageButton button_car_inspection;
    private Button button_confirm;

    Date date;
    String UName;
    String Password;
    String serviceType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button_confirm = (Button)findViewById(R.id.button);
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                make_appointment();
                return_homepage();
            }
        });

        button_wash = (ImageButton)findViewById(R.id.imageButton);
        button_wash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wash();
            }
        });

        button_maintenance = (ImageButton)findViewById(R.id.imageButton2);
        button_maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maintenance();
            }
        });

        button_road_assistance = (ImageButton)findViewById(R.id.imageButton3);
        button_road_assistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                road_assistance();
            }
        });

        button_tire_change = (ImageButton)findViewById(R.id.imageButton4);
        button_tire_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tire_change();
            }
        });

        button_towing = (ImageButton)findViewById(R.id.imageButton5);
        button_towing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                towing();
            }
        });

        button_car_inspection = (ImageButton)findViewById(R.id.imageButton6);
        button_car_inspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                car_inspection();
            }
        });
    }

    private void return_homepage() {
        Intent homepage = new Intent(getApplicationContext(),homepageActivity.class);
        homepage.putExtra("ca.mcgill.ecse321.rsms.android.CURRUNAME",UName);
        homepage.putExtra("ca.mcgill.ecse321.rsms_android.CURRPASSWORD",Password);

        startActivity(homepage);
    }
    private void make_appointment(){

    }
    private void wash(){
        serviceType = "car wash";
    }
    private void maintenance(){
        serviceType = "maintenance";
    }
    private void road_assistance(){
        serviceType = "road assistance";
    }
    private void tire_change(){
        serviceType = "tire change";
    }
    private void towing(){
        serviceType = "towing";
    }
    private void car_inspection(){
        serviceType = "car inspection";
    }
}