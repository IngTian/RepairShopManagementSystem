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
    List<String> types = new ArrayList<String>();
    List<String> plateNos = new ArrayList<String>();
    List<String> times = new ArrayList<String>();
    List<String> prices = new ArrayList<String>();
    List<String> statuses = new ArrayList<String>();
    String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appintment_view_and_pay);

        myListView = (ListView) findViewById(R.id.appointmentListView);

        RequestParams rp = new RequestParams();
        Intent intent = getIntent();

        name = intent.getStringExtra("currentUsername");
        rp.add("username", name);
        HttpUtils.post("/appointment/find_appointments_of_customer", rp, new JsonHttpResponseHandler(){

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                try {
                    if(response != null) {
                        List<JSONObject> services = getValuesForGivenKeyJSON(response, "service");

                        for(int i = 0; i < services.size(); i++){
                            types.add(services.get(i).optString("serviceType"));
                        }


                        plateNos.addAll(getValuesForGivenKey(response, "plateNo"));

                        List<JSONObject> shifts = getValuesForGivenKeyJSON(response, "shift");
                        String date = "";
                        String startTime = "";
                        String endTime = "";

                        for(int i = 0; i < services.size(); i++){
                            date = shifts.get(i).optString("date");
                            startTime = shifts.get(i).optString("startTime");
                            endTime = shifts.get(i).optString("endTime");
                            times.add(date + "/" + startTime + "--" + endTime);
                        }

                        prices.addAll(getValuesForGivenKey(response, "price"));
                        statuses.addAll(getValuesForGivenKey(response, "status"));

                        AppointmentAdapter appointmentAdapter = new AppointmentAdapter(getBaseContext(), types,
                                plateNos, times, prices, statuses);
                        myListView.setAdapter(appointmentAdapter);

                        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent showPaymentActivity = new Intent(getApplicationContext(), AppointmentPaymentActivity.class);
                                showPaymentActivity.putExtra("ca.mcgill.ecse321.rsms_android.TYPE", types.get(position));
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<JSONObject> getValuesForGivenKeyJSON(JSONArray jsonArray, String key) throws JSONException {
        return IntStream.range(0, jsonArray.length())
                .mapToObj(index -> {
                    try {
                        return ((JSONObject) jsonArray.get(index)).optJSONObject(key);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<JSONArray> getValuesForGivenKeyJSONArray(JSONArray jsonArray, String key) throws JSONException {
        return IntStream.range(0, jsonArray.length())
                .mapToObj(index -> {
                    try {
                        return ((JSONObject) jsonArray.get(index)).optJSONArray(key);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

}