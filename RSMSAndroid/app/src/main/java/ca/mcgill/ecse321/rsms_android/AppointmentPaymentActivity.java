package ca.mcgill.ecse321.rsms_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AppointmentPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String status = intent.getStringExtra("ca.mcgill.ecse321.rsms_android.STATUS");

        if (status != "paid"){
            setContentView(R.layout.appointment_payment_paid);
        }else{
            setContentView(R.layout.appointment_payment);
        }
    }
}