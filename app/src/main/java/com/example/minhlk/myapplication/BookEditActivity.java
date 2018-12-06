package com.example.minhlk.myapplication;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class BookEditActivity extends AppCompatActivity {
    TextInputEditText eName,eDesc,eImage;
    ImageButton bSave;
    SpinnerAdapter adapter;
    Spinner authorSpinner;
    List<Author> authors;
    String selectedAuthor;
    Book _book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);
        GlobalBus.getBus().register(this);
        eName = findViewById(R.id.eName);
        eDesc = findViewById(R.id.eDesc);
        eImage = findViewById(R.id.eImage);
        bSave = findViewById(R.id.bSave);
        authorSpinner =  findViewById(R.id.authorSpinner);
        authorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(authors!= null){
                    selectedAuthor =  authors.get(position).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        AuthorService authorService = new AuthorService(this);
        authorService.GetAll();
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            Log.e("hihi","Data Not Found");
        } else {
            _book = (Book) extras.getSerializable("book");
            eName.setText(_book.getName());
            eDesc.setText(_book.getDescription());
            eImage.setText(_book.getImage());
        }
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : POST save
                Book book = new Book();
                //MAP DATA
                book.setId(_book.id);
                book.setName(eName.getText().toString());
                book.setDescription(eDesc.getText().toString());
                book.setIdAuthor(selectedAuthor);
                book.setImage(eImage.getText().toString());

                BookService bookService = new BookService(BookEditActivity.this);
                bookService.Save(book);
            }
        });
    }
    //Listen to getauthor event
    @Subscribe
    protected void getAuthor(Events.AuthorMessage authorMessage){
        this.authors = authorMessage.getMessage();
        adapter = new SpinnerAdapter(this,
                android.R.layout.simple_spinner_item,
                authors);
        authorSpinner.setAdapter(adapter);
    }
}
