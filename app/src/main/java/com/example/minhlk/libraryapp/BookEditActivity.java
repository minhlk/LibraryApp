package com.example.minhlk.myapplication;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookEditActivity extends AppCompatActivity {
    TextInputEditText eName,eDesc,eImage;
    ImageButton bSave;
    SpinnerAdapter adapter;
    Spinner authorSpinner;
    List<Author> authors;
    String selectedAuthor;
    LinearLayout llBookLeft,llBookRight;
    Book _book;
    AuthorService authorService;
    GenreService genreService;
    Set<String> selectedGenre;
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
        llBookLeft = findViewById(R.id.llBookLeft);
        llBookRight = findViewById(R.id.llBookRight);
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

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            Log.e("hihi","Data Not Found");
        } else {
            authorService = new AuthorService(this);
            authorService.GetAll();
            genreService = new GenreService(this);
            genreService.GetAll();
            _book = (Book) extras.getSerializable("book");
            selectedGenre = new HashSet<>(_book.getBookGenreIds());
            eName.setText(_book.getName());
            eDesc.setText(_book.getDescription());
            eImage.setText(_book.getImage());
            selectedAuthor = _book.getIdAuthor();
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
                book.setBookGenreIds(new ArrayList<>(selectedGenre));
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
        authorSpinner.setSelection(authorService.FindById(selectedAuthor,authors));
    }
    @Subscribe
    protected void getGenre(Events.GenreMessage genreMessage){
        List<Genre> genres = genreMessage.getMessage();
//        Collections.sort(genres);
        for (int i = 0; i < genres.size(); i++)
        {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   if(isChecked){
                       selectedGenre.add(buttonView.getId()+"");
                   }
                   else{
                       selectedGenre.remove(buttonView.getId()+"");
                   }
               }
           });

            checkBox.setId(Integer.parseInt(genres.get(i).getId()));
            checkBox.setText(genres.get(i).getName());
            checkBox.setChecked(selectedGenre.contains(genres.get(i).getId()));
            if(i % 2 == 0)
                llBookLeft.addView(checkBox);
            else
                llBookRight.addView(checkBox);
        }
    }
}
