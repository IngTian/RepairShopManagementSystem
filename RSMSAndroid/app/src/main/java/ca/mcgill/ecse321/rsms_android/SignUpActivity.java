package ca.mcgill.ecse321.rsms_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignUpActivity extends AppCompatActivity {
    String username;
    String password;
    String name;
    String email;
    String homeAddress;
    String phoneNo;
    String error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sign_up);

        Button loginButton= (Button) findViewById(R.id.buttonSignUp2);
        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent loginPage=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(loginPage);
            }




        });


        Button signupButton= (Button) findViewById(R.id.buttonSignUp2);
        signupButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final TextView accountusername=(TextView) findViewById(R.id.createTextAddress2);
                username=accountusername.getText().toString();
                final TextView accountPassword=(TextView) findViewById(R.id.createTextPassword2);
                password=accountPassword.getText().toString();
                final TextView accountName=(TextView) findViewById(R.id.editTextTextFirstName);

                final TextView accountPLastName=(TextView) findViewById(R.id.editTextTextLastName);
                name=accountName.getText().toString()+" "+accountPLastName.getText().toString();
                final TextView accountphoneNo=(TextView) findViewById(R.id.editTextPhone);
                phoneNo=accountphoneNo.getText().toString();
                final TextView accountEmail=(TextView) findViewById(R.id.editTextTextEmailAddress);
                email=accountEmail.getText().toString();
                final TextView accountAddress=(TextView) findViewById(R.id.editTextTextAddress);
                homeAddress=accountAddress.getText().toString();
                RequestParams rp=new RequestParams();
                rp.add("username",username);
                rp.add("password",password);
                rp.add("name",name);
                rp.add("phoneNo",phoneNo);
                rp.add("homeAddress",homeAddress);
                rp.add("email",email);
                TextView notification=(TextView) findViewById(R.id.pleaseSignUp);

                HttpUtils.get("/users/customers/get_by_username",rp,new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try{
                            error="Successful";
                            notification.setText(error);


                        }
                        catch(Exception e){
                            error=e.getMessage();
                            notification.setText(error);
                        }
                    }



                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try{
                            error=errorResponse.get("message").toString();}
                        catch(Exception e){
                            error=e.getMessage();
                            notification.setText(error);
                            notification.setText(error);
                        }

                    }
                });


            }});

    }}

