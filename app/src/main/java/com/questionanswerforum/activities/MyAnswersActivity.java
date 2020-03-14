package com.questionanswerforum.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.questionanswerforum.EndPointUrl;
import com.questionanswerforum.Pojo.GetAnswersPojo;
import com.questionanswerforum.Pojo.MyAnswersPojo;
import com.questionanswerforum.R;
import com.questionanswerforum.RetrofitInstance;
import com.questionanswerforum.Utils;
import com.questionanswerforum.adapters.GetAnswersAdapter;
import com.questionanswerforum.adapters.MyAnswersAdapter;
import com.questionanswerforum.adapters.MyAnswersSearchAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAnswersActivity extends AppCompatActivity {
    List<MyAnswersPojo> a1;
    ListView recyclerView;
    MyAnswersSearchAdapter searchAdapter;
    SharedPreferences sharedPreferences;
    String uname;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        uname=sharedPreferences.getString("user_name","");

        getSupportActionBar().setTitle("Get My Questions and Answers");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        a1 = new ArrayList<>();
        recyclerView = (ListView) findViewById(R.id.list_view);
        search=(EditText)findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                searchAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL
//                ,false);recyclerView.setLayoutManager(linearLayoutManager);


//        recyclerAdapter = new MyAnswersAdapter(MyAnswersActivity.this,a1);
//        recyclerView.setAdapter(recyclerAdapter);

        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<MyAnswersPojo>> call = apiService.getMyQuestions1(uname);
        call.enqueue(new Callback<List<MyAnswersPojo>>() {
            @Override
            public void onResponse(Call<List<MyAnswersPojo>> call, Response<List<MyAnswersPojo>> response) {
                a1 = response.body();
               // Toast.makeText(MyAnswersActivity.this,""+response.body(),Toast.LENGTH_LONG).show();
                Log.d("TAG","Response = "+a1);
                searchAdapter=new MyAnswersSearchAdapter(a1,MyAnswersActivity.this);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onFailure(Call<List<MyAnswersPojo>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });
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
