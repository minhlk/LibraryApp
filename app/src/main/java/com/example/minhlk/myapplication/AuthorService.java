package com.example.minhlk.myapplication;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AuthorService {
    Activity c ;

    AuthorService(Activity a){
        this.c = a;
    }
    public void GetAll(){

            RequestQueue queue = Volley.newRequestQueue(c);
            String url ="http://10.0.2.2:44373/api/authors";
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    int size = response.length();
                    List<Author> authors = new ArrayList<>();
                    for(int i = 0 ; i < size ; i++){
                        try {
                            Author author = new Author();
                            author.setId(response.getJSONObject(i).getString("id"));
                            author.setName(response.getJSONObject(i).getString("name"));
                            authors.add(author);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                  GlobalBus.getBus().post(new Events.AuthorMessage(authors));

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(c, "Wrong  ", Toast.LENGTH_SHORT).show();

                }
            }) ;
            queue.add(jsonArrayRequest);


    }
}
