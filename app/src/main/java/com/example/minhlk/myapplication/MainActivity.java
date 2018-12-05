package com.example.minhlk.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    UserService userService;
    TextView tWelcome;
    Button bLogin;
    Button bLogout;
    Button bSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userService  = new UserService(this);
        tWelcome = this.findViewById(R.id.tWelcome);
        bLogin = this.findViewById(R.id.bLogin);
        bLogout = this.findViewById(R.id.bLogout);
        bSearch = this.findViewById(R.id.bSearch);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),LoginActivity.class);
                startActivity(i);
            }
        });
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               userService.Logout();
               onResume();
            }
        });
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Add search activity
                Intent i = new Intent(v.getContext(),BookActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(userService.isLogin()){
            bLogin.setVisibility(View.INVISIBLE);
            bLogout.setVisibility(View.VISIBLE);
            tWelcome.setVisibility(View.VISIBLE);

            tWelcome.setText("Welcome back " );
        }
        else{
            tWelcome.setVisibility(View.INVISIBLE);
            bLogin.setVisibility(View.VISIBLE);
            bLogout.setVisibility(View.INVISIBLE);
        }
    }
}
