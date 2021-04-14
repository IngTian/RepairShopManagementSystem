package ca.mcgill.ecse321.rsms_android;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.entity.mime.Header;

public class EditInfoActivity extends AppCompatActivity {
    Intent intent = getIntent();
    String CurrUName = intent.getStringExtra("ca.mcgill.ecse321.rsms_android.NOWUNAME");
    String CurrPassword = intent.getStringExtra("ca.mcgill.ecse321.rsms_android.NOWPASSWORD");
    Button updButton, backButton;
    String newUName, newPassword, newName, newPhone, newAddress, newEmail, error;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user_info);

        Resources res=getResources();
        newUName = res.getString(R.id.editUserName);
        newPassword = res.getString(R.id.editPassword);
        newName = res.getString(R.id.editPersonName);
        newPhone = res.getString(R.id.editPhone);
        newAddress = res.getString(R.id.editAddress);
        newEmail = res.getString(R.id.editEmail);
        updButton = (Button)findViewById(R.id.updateChangeButton);
        backButton = (Button)findViewById(R.id.goBackButton);

        updButton.setOnClickListener(new AdapterView.OnClickListener(){

            @Override
            public void onClick(View view) {
                //to update user info here
                updateUserInfo(view);
            }
        });
        backButton.setOnClickListener(new AdapterView.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent homePageActivity = new Intent(getApplicationContext(), HomePageActivity.class);
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
    public void updateUserInfo(View v){
        error="";
        final TextView tv = (TextView)findViewById(R.id.updateChangeButton);
        RequestParams rp = new RequestParams();
        rp.add("username", newUName);
        rp.add("password", newPassword);
        rp.add("name", newName);
        rp.add("phone", newPhone);
        rp.add("address", newAddress);
        rp.add("email", newEmail);
        HttpUtils.post("customers/update_info", rp, new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                refreshErrorMessage();
                tv.setText("");
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                try{
                    error += errorResponse.get("message").toString();
                }catch(JSONException e){
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }
    private void refreshErrorMessage(){
        if(error == null || error.length() == 0){}
        else{
            AlertDialog ad;
            AlertDialog.Builder build=new AlertDialog.Builder(EditInfoActivity.this);
            build.setTitle("Oops, an error occurs");
            build.setMessage(error);
            build.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {finish();}
            });
            ad=build.create();
            ad.show();
        }
    }
}