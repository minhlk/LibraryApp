package com.example.minhlk.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    UserService userService ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userService = new UserService(this);
        final TextView tId = findViewById(R.id.tId);
        final TextView tPassword = findViewById(R.id.tPassword);
        findViewById(R.id.bLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userService.Login(tId.getText().toString(),tPassword.getText().toString());


            }
        });

    }
}
