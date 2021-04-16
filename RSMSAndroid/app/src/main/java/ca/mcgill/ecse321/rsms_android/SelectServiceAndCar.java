package ca.mcgill.ecse321.rsms_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SelectServiceAndCar extends AppCompatActivity {
    private Button button_wash;
    private Button button_maintenance;
    private Button button_road_assistance;
    private Button button_tire_change;
    private Button button_towing;
    private Button button_car_inspection;
    private Button button_confirm;

    String date;
    String UName;
    String Password;
    String serviceType = "";
    String error;
    String startTime;
    String endTime;
    String weight;
    String plateNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service_and_car);

        button_confirm = findViewById(R.id.button);
        button_confirm.setOnClickListener(v -> {
            final TextView plateNoView = (TextView) findViewById(R.id.plateNo);
            final TextView weightView= (TextView) findViewById(R.id.weight);
            TextView errorView = (TextView) findViewById(R.id.error);
            plateNo=plateNoView.getText().toString();
            weight = weightView.getText().toString();
            UName = getIntent().getStringExtra("username");
            date = getIntent().getStringExtra("date");
            startTime = getIntent().getStringExtra("timeStart");
            endTime = getIntent().getStringExtra("timeEnd");
            RequestParams rp=new RequestParams();
            rp.add("serviceType",serviceType);
            rp.add("username",UName);
            rp.add("plateNo",plateNo);
            rp.add("date", date);
            rp.add("startTime", startTime.substring(0,5));
            rp.add("endTime", endTime.substring(0,5));
            rp.add("weight", weight);

            HttpUtils.post("/appointment/make_appointment" , rp, new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    System.out.println(response.toString());
                    refreshErrorMessage();
                    ((TextView) findViewById(R.id.error)).setText(response.toString());
                    back_to_homepage();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    try {
                        error += errorResponse.get("message").toString();
                    } catch (JSONException e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();
                }
            });
        });

        button_wash = findViewById(R.id.Button);
        button_wash.setOnClickListener(v -> wash());

        button_maintenance = findViewById(R.id.Button2);
        button_maintenance.setOnClickListener(v -> maintenance());

        button_road_assistance = findViewById(R.id.Button3);
        button_road_assistance.setOnClickListener(v -> road_assistance());

        button_tire_change = findViewById(R.id.Button4);
        button_tire_change.setOnClickListener(v -> tire_change());

        button_towing = findViewById(R.id.Button5);
        button_towing.setOnClickListener(v -> towing());

        button_car_inspection = findViewById(R.id.Button6);
        button_car_inspection.setOnClickListener(v -> car_inspection());

    }

    private void make_appointment(){
        RequestParams rp=new RequestParams();
        rp.add("serviceType",serviceType);
        rp.add("username",UName);
        rp.add("plateNo",plateNo);
        rp.add("date", date);
        rp.add("startTime", startTime);
        rp.add("endTime", endTime);
        rp.add("weight", weight);

        HttpUtils.post("/appointment/make_appointment" , rp, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                ((TextView) findViewById(R.id.error)).setText("");
                back_to_homepage();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
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
    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }
    private void back_to_homepage(){
        Intent homepage = new Intent(getApplicationContext(), HomePageActivity.class);
        homepage.putExtra("ca.mcgill.ecse321.rsms.android.CURRUNAME",UName);
        homepage.putExtra("ca.mcgill.ecse321.rsms_android.CURRPASSWORD",Password);

        startActivity(homepage);
    }

}