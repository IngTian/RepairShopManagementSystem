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
        String type = intent.getStringExtra("ca.mcgill.ecse321.rsms_android.TYPE");
        String status  = intent.getStringExtra("ca.mcgill.ecse321.rsms_android.STATUS");
        String price = "FREE";

        if (status != "paid"){
            setContentView(R.layout.appointment_payment);
            TextView priceForPayTextView = (TextView) findViewById(R.id.priceForPayTextView);
            if (type.equals("car wash")){
                price = "$30";
            }else if(type.equals("maintenance")) {
                price = "$80";
            }else if(type.equals("repair")) {
                price = "$150";
            }else if(type.equals("changeTire")) {
                price = "$30";
            }
            priceForPayTextView.setText(price);
        }else{
            setContentView(R.layout.appointment_payment_paid);
        }
    }
}