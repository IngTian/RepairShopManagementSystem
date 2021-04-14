package ca.mcgill.ecse321.rsms_android;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CarMActivity extends AppCompatActivity {

String plateNo;
String model;
String year;
String manufacturer;
String error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_management);
        Intent  carPage=getIntent();
        String name= carPage.getStringExtra("currentUsername");
        final TextView CarPlateNo=(TextView) findViewById(R.id.CarPlateNo);
        final TextView CarYear=(TextView) findViewById(R.id.CarYear);
        final TextView CarModel=(TextView) findViewById(R.id.CarModel);
        final TextView CarManufacturer=(TextView) findViewById(R.id.CarManufacturer);
        final TextView notification=(TextView) findViewById(R.id.Title2);
       Button addCarButton=(Button) findViewById(R.id.AddCarButton);
        addCarButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                plateNo = CarPlateNo.getText().toString();
                model = CarModel.getText().toString();
                year = CarYear.getText().toString();
                manufacturer = CarManufacturer.getText().toString();
                RequestParams rp = new RequestParams();
                rp.add("username", name);
                rp.add("plateNo", plateNo);
                rp.add("model", model);
                rp.add("year", year);
                rp.add("manufacturer", manufacturer);
                HttpUtils.post("/users/cars/create_android", rp, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            if(response==null)
                                error="Invalid form";
                            else{
                            error = "Successful";}
                            notification.setText(error);
                            String temp = "";
                            CarPlateNo.setText(temp);
                            CarModel.setText(temp);
                            CarYear.setText(temp);
                            CarManufacturer.setText(temp);


                        } catch (Exception e) {
                            error = e.getMessage();
                            notification.setText(error);
                        }
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {
                            error = errorResponse.get("message").toString();
                        } catch (Exception e) {
                            error = e.getMessage();
                            notification.setText(error);

                        }

                    }
                });


            }});


        Button updateCarButton = (Button) findViewById(R.id.UpdateCarButton);
        updateCarButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                plateNo = CarPlateNo.getText().toString();
                model = CarModel.getText().toString();
                year = CarYear.getText().toString();
                manufacturer = CarManufacturer.getText().toString();
                RequestParams rp = new RequestParams();
                rp.add("newUsername", name);
                rp.add("plateNo", plateNo);
                rp.add("newModel", model);
                rp.add("newYear", year);
                rp.add("newManufacturer", manufacturer);
                HttpUtils.put("/users/cars/update_android", rp, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {

                            error = "Successful";
                            notification.setText(error);
                            String temp = "";
                            CarPlateNo.setText(temp);
                            CarModel.setText(temp);
                            CarYear.setText(temp);
                            CarManufacturer.setText(temp);


                        } catch (Exception e) {
                            error = e.getMessage();
                            notification.setText(error);
                        }
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {
                            error = errorResponse.get("message").toString();
                        } catch (Exception e) {
                            error = e.getMessage();
                            notification.setText(error);
                            notification.setText(error);
                        }

                    }
                });
            }


        });

    }}
