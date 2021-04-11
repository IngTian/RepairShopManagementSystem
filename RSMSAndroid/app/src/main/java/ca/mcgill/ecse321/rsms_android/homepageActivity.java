package ca.mcgill.ecse321.rsms_android;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

public class homepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_home_page);
        Button viewCarButton=(Button) findViewById(R.id.viewCars);
       viewCarButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent carMA=new Intent(getApplicationContext(),CarMActivity.class);
                startActivity(carMA);
            }
        });


        Button changeInfoButton=(Button) findViewById(R.id.changeInfoButton);
        changeInfoButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent InfoManagement=new Intent(getApplicationContext(),EditInfoActivity.class);
                startActivity(InfoManagement);
            }
        });

        Button viewAppointmentButton=(Button) findViewById(R.id.viewAppointment);
        viewAppointmentButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent viewAppointment=new Intent(getApplicationContext(),ViewAppointmentActivity.class);
                startActivity(viewAppointment);
            }
        });

        Button makeAppointmentButton=(Button) findViewById(R.id.makeAppointment);
        makeAppointmentButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent makeAppointment=new Intent(getApplicationContext(),ViewAppointmentActivity.class);
                startActivity(makeAppointment);
            }
        });
    }
}