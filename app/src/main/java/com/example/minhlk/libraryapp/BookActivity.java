package com.example.minhlk.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SearchView svBook ;
    int currentPage = 0;
    int pageSize = -1;
    String nameKeyword = "";
    List<Book> books = new ArrayList<>();
    protected void Init(){
        pageSize = -1;
        currentPage = 0;
        books.removeAll(books);
        (new LoadBookAsync()).execute(currentPage);
        currentPage++;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        //INIT VIEW MAIN
        mRecyclerView = findViewById(R.id.lBooks);
        svBook = findViewById(R.id.svBook);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        Fresco.initialize(this);
        mAdapter = new BookAdapter(books,mRecyclerView);

        Init();
        svBook.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                nameKeyword = query;
                Init();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                if(newText.isEmpty()){
                    nameKeyword = "";
                    Init();
                }
                return true;
            }
        });
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
            public void onItemClick(Book book) {
                //TODO : Add detail activity,add edit button

                Intent i = new Intent(BookActivity.this,BookEditActivity.class);
                i.putExtra("book",book);
                BookActivity.this.startActivityForResult(i,1);
            }
        });
        mRecyclerView.setAdapter(mAdapter);


    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Init();
            }
        }
    }
    public class LoadBookAsync extends AsyncTask<Integer, Void, Void> {
        @Override
        protected void onPreExecute() {
            if(pageSize == -1 ){
                String url = "http://10.0.2.2:44373/api/Books/size?searchKeyWords="+nameKeyword;
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
            String url = "http://10.0.2.2:44373/api/Books?page=" + page[0] + "&searchKeyWords="+nameKeyword;
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(final JSONArray response) {

                    Book temp ;
                    for (int i = 0; i < 10; i++) {
                        try {
                            temp = new Book();
                            temp.setId(response.getJSONObject(i).getString("id"));
                            temp.setName(response.getJSONObject(i).getString("name"));
                            temp.setDescription(response.getJSONObject(i).getString("description"));
                            temp.setAuthorName(response.getJSONObject(i).getJSONObject("idAuthorNavigation").getString("name"));
                            temp.setIdAuthor(response.getJSONObject(i).getString("idAuthor"));
                            temp.setImage(response.getJSONObject(i).getString("image"));
                            JSONArray bgResult = response.getJSONObject(i).getJSONArray("bookGenre");
                            List<BookGenre> bookGenre = new ArrayList<>();
                            for(int j = 0 ; j < bgResult.length(); j++){
                                bookGenre.add(new BookGenre(bgResult.getJSONObject(j).getString("idGenre")));
                            }
                            temp.setBookGenre(bookGenre);
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
