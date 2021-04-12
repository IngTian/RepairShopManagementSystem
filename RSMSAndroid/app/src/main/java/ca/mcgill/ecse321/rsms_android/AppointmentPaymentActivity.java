package ca.mcgill.ecse321.rsms_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AppointmentPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String status = intent.getStringExtra("ca.mcgill.ecse321.rsms_android.STATUS");
        String price  = intent.getStringExtra("ca.mcgill.ecse321.rsms_android.PRICE");

        if (status != "paid"){
            setContentView(R.layout.appointment_payment);
            TextView priceForPayTextView = (TextView) findViewById(R.id.priceForPayTextView);
            priceForPayTextView.setText(price);
        }else{
            setContentView(R.layout.appointment_payment_paid);
        }
    }
}