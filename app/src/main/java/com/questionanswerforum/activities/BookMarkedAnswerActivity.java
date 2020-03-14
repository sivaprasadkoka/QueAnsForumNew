package com.questionanswerforum.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.questionanswerforum.Pojo.QueAnsPojo;
import com.questionanswerforum.R;
import com.questionanswerforum.adapters.BookmarkAdapter;

import java.util.List;

public class BookMarkedAnswerActivity extends AppCompatActivity {
    ListView list_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        getSupportActionBar().setTitle("BookMarked Data");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list_view=(ListView)findViewById(R.id.list_view);
        DatabaseHandler db=new DatabaseHandler(BookMarkedAnswerActivity.this);
        List<QueAnsPojo> lQueAns = db.getQueAns();
        if(lQueAns!=null){
            list_view.setAdapter(new BookmarkAdapter(lQueAns,BookMarkedAnswerActivity.this));
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
