package ca.mcgill.ecse321.rsms_android;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_home_page);

        Intent intent2=getIntent();

        String username=intent2.getStringExtra("currentName");
        Intent intent=getIntent();
        String CurrUName=intent.getStringExtra("ca.mcgill.ecse321.rsms_android.CURRUNAME");
        String CurrPassword=intent.getStringExtra("ca.mcgill.ecse321.rsms_android.CURRPASSWORD");
        Button viewCarButton = (Button) findViewById(R.id.viewCars);
        viewCarButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent carMA = new Intent(getApplicationContext(), CarMActivity.class);

                carMA.putExtra("currentUsername",username);
                startActivity(carMA);
            }
        });


        Button changeInfoButton = (Button) findViewById(R.id.changeInfoButton);
        changeInfoButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent InfoManagement = new Intent(getApplicationContext(), EditInfoActivity.class);
                InfoManagement.putExtra("ca.mcgill.ecse321.rsms.android.NOWUNAME",CurrUName);
                InfoManagement.putExtra("ca.mcgill.ecse321.rsms_android.NOWPASSWORD",CurrPassword);
                startActivity(InfoManagement);
            }
        });

        Button viewAppointmentButton=(Button) findViewById(R.id.viewAppointment);
        viewAppointmentButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent viewAppointment=new Intent(getApplicationContext(), AppointmentViewAndPayActivity.class);
                startActivity(viewAppointment);
            }
        });

        Button makeAppointmentButton=(Button) findViewById(R.id.makeAppointment);
        makeAppointmentButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent makeAppointment=new Intent(getApplicationContext(),MakeAppointmentSelectDate.class);
                makeAppointment.putExtra("username", username);
                startActivity(makeAppointment);
            }
        });

        Button logOutButton=(Button) findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent logout=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(logout);
            }
        });
    }
}