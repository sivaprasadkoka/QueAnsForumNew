package com.questionanswerforum.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.questionanswerforum.EndPointUrl;
import com.questionanswerforum.Pojo.GetAnswersPojo;
import com.questionanswerforum.Pojo.MyAnswersPojo;
import com.questionanswerforum.R;
import com.questionanswerforum.RetrofitInstance;
import com.questionanswerforum.activities.DatabaseHandler;
import com.questionanswerforum.activities.GetAnswersActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyAnswersSearchAdapter extends BaseAdapter {
    List<MyAnswersPojo> ar9,ar1;
    Context cnt;
    String idvariable;
    DatabaseHandler db;
    public MyAnswersSearchAdapter(List<MyAnswersPojo> ar, Context cnt)
    {
        db=new DatabaseHandler(cnt);
        this.ar9=ar;
        this.cnt=cnt;
        this.ar1 = new ArrayList<MyAnswersPojo>();
        ar1.addAll(ar);
    }
    @Override
    public int getCount() {
        return ar1.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup)
    {
        LayoutInflater obj1 = (LayoutInflater)cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2=obj1.inflate(R.layout.recyclerview_child,null);
        TextView tv_postedby=(TextView) obj2.findViewById(R.id.tv_postedby);

        tv_postedby.setText("Post By  :"+ar1.get(position).getPost_by());
        TextView tv_subject=(TextView) obj2.findViewById(R.id.tv_subject);
        tv_subject.setText(ar1.get(position).getQuestion());
        TextView tv_qstn=(TextView) obj2.findViewById(R.id.tv_qstn);
        tv_qstn.setText(ar1.get(position).getQuestion());
        if(db.isBookQueMarked(ar1.get(position).getQuestion())) {
            tv_qstn.setBackgroundColor(Color.parseColor("#94f2ac"));
        }
        TextView tv_tags=(TextView) obj2.findViewById(R.id.tv_tags);
        tv_tags.setText(ar1.get(position).getTags());


        final TextView tv_ans1=(TextView)obj2.findViewById(R.id.tv_ans1);
        final TextView tv_ans2=(TextView)obj2.findViewById(R.id.tv_ans2);
        final TextView  tv_ans3=(TextView)obj2.findViewById(R.id.tv_ans3);


        final Button btn_view_more=(Button) obj2.findViewById(R.id.btn_view_more);
        btn_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt, GetAnswersActivity.class);
                intent.putExtra("id",ar1.get(position).getId());
                cnt.startActivity(intent);

            }
        });
        final LinearLayout ll=(LinearLayout)obj2.findViewById(R.id.ll);
        Button btn_ans=(Button) obj2.findViewById(R.id.btn_ans);
        btn_ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ll.getVisibility()==View.VISIBLE){
                    ll.setVisibility(View.GONE);
                }else{
                    submitdata(tv_ans1,tv_ans2,tv_ans3,ar1.get(position).getId(),btn_view_more);
                    ll.setVisibility(View.VISIBLE);
                }

            }

        });


        return obj2;
    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ar1.clear();
        if (charText.length() == 0) {
            ar1.addAll(ar9);
        }
        else
        {
            for (MyAnswersPojo wp : ar9)
            {
                if (wp.getQuestion().toLowerCase(Locale.getDefault()).contains(charText)||wp.getTags().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    ar1.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    List<GetAnswersPojo> a1=null;

    public void submitdata(final TextView tv_ans1,final TextView tv_ans2,final TextView tv_ans3,final String id,final Button btn_view_more){
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<GetAnswersPojo>> call = apiService.getAnswers(id);
        call.enqueue(new Callback<List<GetAnswersPojo>>() {
            @Override
            public void onResponse(Call<List<GetAnswersPojo>> call, Response<List<GetAnswersPojo>> response) {
                a1 = response.body();
                //Toast.makeText(cnt,"dfdfdf"+response.body().size(),Toast.LENGTH_LONG).show();
                if(a1!=null) {
                    if(a1.size() >0){
                        btn_view_more.setVisibility(View.VISIBLE);
                        if(a1.size()==1){
                            GetAnswersPojo user = a1.get(0);
                            tv_ans1.setText("1) "+user.getAnswer());
                            if(db.isBookMarked(user.getAnswer())){
                                tv_ans1.setTextColor(Color.parseColor("#4CAF50"));
                            }
                        }
                        if(a1.size()==2){
                            GetAnswersPojo user1 = a1.get(0);
                            tv_ans1.setText("1) "+user1.getAnswer());

                            GetAnswersPojo user2 = a1.get(1);
                            tv_ans2.setText("2) "+user2.getAnswer());

                            if(db.isBookMarked(user1.getAnswer())){
                                tv_ans1.setTextColor(Color.parseColor("#4CAF50"));
                            }
                            if(db.isBookMarked(user2.getAnswer())){
                                tv_ans2.setTextColor(Color.parseColor("#4CAF50"));
                            }
                        }
                        if(a1.size()>=3){
                            GetAnswersPojo user1 = a1.get(0);
                            tv_ans1.setText("1) "+user1.getAnswer());
                            GetAnswersPojo user2 = a1.get(1);
                            tv_ans2.setText("2) "+user2.getAnswer());
                            GetAnswersPojo user3 = a1.get(2);
                            tv_ans3.setText("3) "+user3.getAnswer());
                            if(db.isBookMarked(user1.getAnswer())){
                                tv_ans1.setTextColor(Color.parseColor("#4CAF50"));
                            }
                            if(db.isBookMarked(user2.getAnswer())){
                                tv_ans2.setTextColor(Color.parseColor("#4CAF50"));
                            }
                            if(db.isBookMarked(user3.getAnswer())){
                                tv_ans3.setTextColor(Color.parseColor("#4CAF50"));
                            }
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<List<GetAnswersPojo>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });
    }
    }


