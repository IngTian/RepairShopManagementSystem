package ca.mcgill.ecse321.rsms_android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

public class EditInfoActivity extends AppCompatActivity {
    Button updButton;
    String newUName;
    String newPassword;
    String newName;
    String newPhone;
    String newAddress;
    String newEmail;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user_info);

        Resources res=getResources();
        newUName=res.getString(R.id.editUserName);
        newPassword=res.getString(R.id.editPassword);
        newName=res.getString(R.id.editPersonName);
        newPhone=res.getString(R.id.editPhone);
        newAddress=res.getString(R.id.editAddress);
        newEmail=res.getString(R.id.editEmail);
        updButton=(Button)findViewById(R.id.updateChangeButton);

        updButton.setOnClickListener(new AdapterView.OnClickListener(){

            @Override
            public void onClick(View view) {
                //to update user info here
            }
        });
    }
}