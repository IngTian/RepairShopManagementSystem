package ca.mcgill.ecse321.rsms_android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cz.msebera.android.httpclient.Header;

public class AppointmentViewAndPayActivity extends AppCompatActivity {

    String name = "";
    ListView myListView;
    List<String> appointments;
    List<String> plateNos;
    List<String> times;
    List<String> prices;
    List<String> statuses;
    String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appintment_view_and_pay);

        myListView = (ListView) findViewById(R.id.appointmentListView);

        RequestParams rp = new RequestParams();
        Intent intent = getIntent();

        name = intent.getStringExtra("currentUsername");
        rp.add("name", name);
        HttpUtils.post("/appointment/find_appointments_of_customer", rp, new JsonHttpResponseHandler(){

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    if(response != null) {

                        appointments.addAll(getValuesForGivenKey(response, "appointmentId"));
                        plateNos.addAll(getValuesForGivenKey(response, "plateNo"));
                        times.addAll(getValuesForGivenKey(response, "time"));
                        prices.addAll(getValuesForGivenKey(response, "price"));
                        statuses.addAll(getValuesForGivenKey(response, "status"));

                        AppointmentAdapter appointmentAdapter = new AppointmentAdapter(getBaseContext(), appointments,
                                plateNos, times, prices, statuses);
                        myListView.setAdapter(appointmentAdapter);

                        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent showPaymentActivity = new Intent(getApplicationContext(), AppointmentPaymentActivity.class);
                                showPaymentActivity.putExtra("ca.mcgill.ecse321.rsms_android.PRICE", prices.get(position));
                                showPaymentActivity.putExtra("ca.mcgill.ecse321.rsms_android.STATUS", statuses.get(position));
                                startActivity(showPaymentActivity);
                            }
                        });
                    }
                    else{
                        error = "No appointment!";
                    }

                } catch (Exception e) {
                    error = e.getMessage();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                try{
                    error = errorResponse.get("message").toString();}

                catch(Exception e){
                    error = e.getMessage();
                }

                Intent pageBack = new Intent(getApplicationContext(), MakeAppointmentSelectDate.class);
                startActivity(pageBack);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> getValuesForGivenKey(JSONArray jsonArray, String key) throws JSONException {
        return IntStream.range(0, jsonArray.length())
                .mapToObj(index -> {
                    try {
                        return ((JSONObject) jsonArray.get(index)).optString(key);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }
}