package com.questionanswerforum.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.questionanswerforum.EndPointUrl;
import com.questionanswerforum.Pojo.GetAnswersPojo;
import com.questionanswerforum.R;
import com.questionanswerforum.ResponseData;
import com.questionanswerforum.RetrofitInstance;
import com.questionanswerforum.Utils;
import com.questionanswerforum.adapters.GetAnswersAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllAnswersActivity extends AppCompatActivity {
    List<GetAnswersPojo> a1;
    RecyclerView recyclerView;
    GetAnswersAdapter recyclerAdapter;
Button btnSubmit;
    EditText etAnswer;
    String qustn_ID;
    TextView tvQuestion;
    public static String question="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_answer);
        tvQuestion=(TextView)findViewById(R.id.tvQuestion);

        Intent i=getIntent();
        qustn_ID=i.getStringExtra("qstn_id");
        tvQuestion.setText("Que : "+i.getStringExtra("qstn"));
        question = i.getStringExtra("qstn");
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
        etAnswer=(EditText)findViewById(R.id.etAnswer);
        getSupportActionBar().setTitle("Answer");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Toast.makeText(getApplicationContext(),getIntent().getStringExtra("id"),Toast.LENGTH_LONG).show();
        a1 = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL
                ,false);recyclerView.setLayoutManager(linearLayoutManager);


        recyclerAdapter = new GetAnswersAdapter(AllAnswersActivity.this,a1);
        recyclerView.setAdapter(recyclerAdapter);

        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<GetAnswersPojo>> call = apiService.getAnswers(getIntent().getStringExtra("id"));
        call.enqueue(new Callback<List<GetAnswersPojo>>() {
            @Override
            public void onResponse(Call<List<GetAnswersPojo>> call, Response<List<GetAnswersPojo>> response) {
                a1 = response.body();
                //Log.d("TAG","Response = "+a1);
                recyclerAdapter.setMovieList(a1);
            }

            @Override
            public void onFailure(Call<List<GetAnswersPojo>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });
    }
    ProgressDialog progressDialog;
    private void submitData() {
        String ans = etAnswer.getText().toString();

        progressDialog = new ProgressDialog(AllAnswersActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = service.post_answer(qustn_ID,ans,sharedPreferences.getString("user_name","def"));
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                if (response.body().status.equals("true")) {
                    progressDialog.dismiss();
                    Toast.makeText(AllAnswersActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    Toast.makeText(AllAnswersActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AllAnswersActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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