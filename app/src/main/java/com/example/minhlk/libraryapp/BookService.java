package com.example.minhlk.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookService {
    Activity _activity;
    UserService _userService;
    public BookService(Activity c ){
        this._activity = c;
        _userService = new UserService(c);
    }
    public void Save(final Book book){
        if(_userService.isLogin())
        try {
            RequestQueue queue = Volley.newRequestQueue(_activity);
            String url ="http://10.0.2.2:44373/api/books/"+book.getId();
            Gson gson = new Gson();
            JSONObject jsonBody = new JSONObject(gson.toJson(book));
            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Toast.makeText(_activity, "Save success", Toast.LENGTH_SHORT).show();
                    //TODO
                    Intent i = new Intent();
                    _activity.setResult(Activity.RESULT_OK,i);
                    _activity.finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("hihi",error.toString());
                    Toast.makeText(_activity, "Something went wrong ", Toast.LENGTH_SHORT).show();

                }
            }){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", "Bearer "+ _userService.sharedpreferences.getString("token",""));
                    return params;
                }
            } ;
            queue.add(jsonOblect);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        else
            Toast.makeText(_activity,"Unauthorized",Toast.LENGTH_SHORT).show();
    }
}
