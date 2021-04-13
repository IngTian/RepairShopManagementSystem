package ca.mcgill.ecse321.rsms_android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.content.res.Resources;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.cache.Resource;

public class MainActivity extends AppCompatActivity {
    String UName;
    String Password;
    String error="";

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button loginButton = (Button) findViewById(R.id.buttonLogin1);
        Resources res=getResources();
        UName=res.getString(R.id.accountUName);
        Password=res.getString(R.id.accountPassword);
        loginButton.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View v) {

             Intent homepage = new Intent(getApplicationContext(), homepageActivity.class);
             final TextView accountName=(TextView) findViewById(R.id.accountUName);
             final TextView accountPassword=(TextView) findViewById(R.id.accountPassword);
             final TextView errorMe=(TextView) findViewById(R.id.errorMessage);
             String username=accountName.getText().toString();
             String accpassword=accountPassword.getText().toString();
             RequestParams rp=new RequestParams();
             rp.add("username",username);
             HttpUtils.get("/customers/get_by_username",rp,new JsonHttpResponseHandler(){

                 @Override
                 public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                     try{
                         String password=response.getString("password");
                         if(accpassword.equals(password)){
                             startActivity(homepage);
                             homepage.putExtra("ca.mcgill.ecse321.rsms.android.CURRUNAME",UName);
                             homepage.putExtra("ca.mcgill.ecse321.rsms_android.CURRPASSWORD",Password);
                         }
                         else{
                             error="incorrect password";
                             errorMe.setText(error);
                         }

                     }
                     catch(Exception e){
                         error=e.getMessage();
                         errorMe.setText(error);
                     }
                     }



                 @Override
                 public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                     try{
                     error=errorResponse.get("message").toString();}
                     catch(Exception e){
                         error=e.getMessage();
                         errorMe.setText(error);
                     }
                     Intent fortest=new Intent(getApplicationContext(),EditInfoActivity.class);
                     startActivity(fortest);
                 }
             });







         }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}