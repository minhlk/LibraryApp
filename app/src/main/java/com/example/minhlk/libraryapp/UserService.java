package com.example.minhlk.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserService {
    Activity c;

    SharedPreferences sharedpreferences;
    public UserService(Activity a ){
        this.c = a;
        sharedpreferences = c.getSharedPreferences("CurrentUserInfo", Context.MODE_PRIVATE);

    }
    public void Login(String userName , String passWord){

        try {
            RequestQueue queue = Volley.newRequestQueue(c);
            String url ="http://10.0.2.2:44373/api/Users/login";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("userName", "minhm");
            jsonBody.put("Password", "minh@123");

            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Toast.makeText(c, "Login Success", Toast.LENGTH_SHORT).show();
                    StoreUser(response);
                    c.finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(c, "Wrong UserName or Password ", Toast.LENGTH_SHORT).show();

                }
            }) ;
            queue.add(jsonOblect);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void StoreUser(JSONObject res){

        try {

            SharedPreferences.Editor edit =  sharedpreferences.edit();
            JSONObject user = res.getJSONObject("result");
            edit.putString("userName" ,user.getString("userName"));
            edit.putString("token" ,user.getString("token"));
            edit.apply();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public boolean isLogin(){
        if(sharedpreferences != null && sharedpreferences.getString("userName",null) != null)
            return true;
        return false;

    }
    public void Logout(){
        if(isLogin()){
            sharedpreferences.edit().clear().apply();
            Toast.makeText(c, "Logout successfully", Toast.LENGTH_SHORT).show();
        }
    }
}

