package ca.mcgill.ecse321.rsms_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ShowShifts extends AppCompatActivity {

    ListView myListView;
    String[] shiftId;
    String[] time;
    String[] date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shifts);

        Resources res = getResources();
        myListView = (ListView) findViewById(R.id.shiftsListView);
        shiftId = res.getStringArray(R.array.shiftId);
        time = res.getStringArray(R.array.time);
        date = res.getStringArray(R.array.date);

        ShiftAdapter shiftAdapter = new ShiftAdapter(this, shiftId, time, date);
        myListView.setAdapter(shiftAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent selectServiceAndCar = new Intent(getApplicationContext(), SelectServiceAndCar.class);
                selectServiceAndCar.putExtra("shiftId", shiftId[position]);
                startActivity(selectServiceAndCar);
            }
        });
    }
}