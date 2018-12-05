package com.example.minhlk.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int currentPage = 0;
    int pageSize = -1;
    List<Book> books = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        mRecyclerView = findViewById(R.id.lBooks);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)
        mAdapter = new BookAdapter(books,mRecyclerView);
        (new LoadBookAsync()).execute(currentPage);
        currentPage++;
        ((BookAdapter) mAdapter).setOnLoadMoreListener(new BookAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                if (books.size() < pageSize || pageSize == -1) {
                    (new LoadBookAsync()).execute(currentPage);
                    currentPage++;
                }
                 else {
                    Toast.makeText(BookActivity.this, "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ((BookAdapter) mAdapter).setOnItemListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book item) {
                //TODO : Add detail activity,add edit button
                Toast.makeText(BookActivity.this,item.getBookName(),Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }
    public class LoadBookAsync extends AsyncTask<Integer, Void, Void> {
        @Override
        protected void onPreExecute() {
            if(pageSize == -1 ){
                String url = "http://10.0.2.2:44373/api/Books/size";
                RequestQueue queue = Volley.newRequestQueue(BookActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pageSize = Integer.parseInt(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("hihi",error.toString());
//                        mTextView.setText("That didn't work!");
                    }
                });
                queue.add(stringRequest);
            }
        }

        @Override
        protected Void doInBackground(Integer... page) {

            RequestQueue queue = Volley.newRequestQueue(BookActivity.this);
            String url = "http://10.0.2.2:44373/api/Books?page=" + page[0];
//            books.add(null);
//            mAdapter.notifyItemInserted(books.size() - 1);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(final JSONArray response) {

//                    books.remove(books.size() - 1);
//                    mAdapter.notifyItemRemoved(books.size());

                    //Generating more data
                    Book temp ;
                    for (int i = 0; i < 10; i++) {
                        try {
                            temp = new Book();
                            temp.setBookName(response.getJSONObject(i).getString("name"));
                            temp.setDescription(response.getJSONObject(i).getString("description"));
                            books.add(temp);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    ((BookAdapter) mAdapter).setLoaded();
                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("hihi", error.toString());
                    Toast.makeText(BookActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                }
            });
            queue.add(jsonArrayRequest);
            return null;
        }

    }
}
