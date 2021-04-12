package ca.mcgill.ecse321.rsms_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class AppointmentViewAndPayActivity extends AppCompatActivity {

    ListView myListView;
    String[] appointments;
    String[] plateNos;
    String[] times;
    String[] prices;
    String[] statuses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appintment_view_and_pay);

        Resources res = getResources();
        myListView = (ListView) findViewById(R.id.appointmentListView);
        appointments = res.getStringArray(R.array.appointments);
        plateNos = res.getStringArray(R.array.plateNos);
        times = res.getStringArray(R.array.times);
        prices = res.getStringArray(R.array.prices);
        statuses = res.getStringArray(R.array.statuses);

        AppointmentAdapter appointmentAdapter = new AppointmentAdapter(this, appointments,
                plateNos, times, prices, statuses);
        myListView.setAdapter(appointmentAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showPaymentActivity = new Intent(getApplicationContext(), AppointmentPaymentActivity.class);
                showPaymentActivity.putExtra("ca.mcgill.ecse321.rsms_android.PRICE", prices[position]);
                showPaymentActivity.putExtra("ca.mcgill.ecse321.rsms_android.STATUS", statuses[position]);
                startActivity(showPaymentActivity);
            }
        });
    }
}