package ca.mcgill.ecse321.rsms_android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
public class EditInfoActivity extends AppCompatActivity {
        Button updButton, backButton;
        String newUName, newPassword, newName, newPhone, newAddress, newEmail,CurrUName;
        @SuppressLint("ResourceType")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.update_user_info);
            Intent intent=getIntent();
            CurrUName=intent.getStringExtra("ca.mcgill.ecse321.rsms.android.NOWUNAME");

            Resources res=getResources();
            updButton = (Button)findViewById(R.id.updateChangeButton);
            backButton = (Button)findViewById(R.id.goBackButton);

            updButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    //to update user info here
                    final TextView unameText=(TextView)findViewById(R.id.editUserName);
                    newUName =unameText.getText().toString();
                    final TextView passwordText=(TextView)findViewById(R.id.editPassword);
                    newPassword =passwordText.getText().toString();
                    final TextView nameText=(TextView)findViewById(R.id.editPersonName);
                    newName =nameText.getText().toString();
                    final TextView phoneText=(TextView)findViewById(R.id.editPhone);
                    newPhone =phoneText.getText().toString();
                    final TextView addressText=(TextView)findViewById(R.id.editAddress);
                    newAddress =addressText.getText().toString();
                    final TextView emailText=(TextView)findViewById(R.id.editEmail);
                    newEmail =emailText.getText().toString();
                    TextView tv=(TextView)findViewById(R.id.errors);
                    RequestParams rp=new RequestParams();
                    rp.add("newUsername",newUName);
                    rp.add("newPassword",newPassword);
                    rp.add("newName",newName);
                    rp.add("newPhoneNo",newPhone);
                    rp.add("newAddress",newAddress);
                    rp.add("newEmail",newEmail);
                    rp.add("username",CurrUName);
                    System.out.println(CurrUName);
                    HttpUtils.put("/users/customers/update_info_android",rp,new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            String error="";
                            try{
                                error=response.toString();
                                if(error.contains("true")){
                                    String[] errors=error.split(":");
                                    error=errors[2];
                                }else error="Successful";
                                tv.setText(error);
                            }catch(Exception e){
                                error=e.getMessage();
                                tv.setText(error);
                            }
                        }
                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            String error="An error occurs:";
                            try{
                                error+=errorResponse.get("message").toString();
                            }catch(Exception e){
                                error+=e.getMessage();
                                tv.setText(error);
                            }
                        }
                    });
                }
            });
            backButton.setOnClickListener(new AdapterView.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Intent homePageActivity=new Intent(getApplicationContext(),HomePageActivity.class);
                    homePageActivity.putExtra("ca.mcgill.ecse321.rsms_android.UPDUNAME",newUName);
                    homePageActivity.putExtra("ca.mcgill.ecse321.rsms_android.UPDPASSWORD",newPassword);
                    homePageActivity.putExtra("ca.mcgill.ecse321.rsms_android.UPDNAME",newName);
                    homePageActivity.putExtra("ca.mcgill.ecse321.rsms_android.UPDPHONE",newPhone);
                    homePageActivity.putExtra("ca.mcgill.ecse321.rsms_android.UPDADDRESS",newAddress);
                    homePageActivity.putExtra("ca.mcgill.ecse321.rsms_android.UPDEMAIL",newEmail);
                    startActivity(homePageActivity);
                }
            });
        }
    }
