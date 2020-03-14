package com.questionanswerforum.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.questionanswerforum.EndPointUrl;
import com.questionanswerforum.Pojo.CommentsPojo;
import com.questionanswerforum.Pojo.GetAnswersPojo;
import com.questionanswerforum.R;
import com.questionanswerforum.RetrofitInstance;
import com.questionanswerforum.activities.AddCommentActivity;
import com.questionanswerforum.activities.AllAnswersActivity;
import com.questionanswerforum.activities.DatabaseHandler;
import com.questionanswerforum.activities.ViewCommentActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAnswersAdapter extends RecyclerView.Adapter<GetAnswersAdapter.MyviewHolder> {

    Context context;
    List<GetAnswersPojo> a1;
    DatabaseHandler db;

    public GetAnswersAdapter(Context context, List<GetAnswersPojo> movieList) {
        this.context = context;
         db=new DatabaseHandler(context);
        this.a1 = movieList;
    }

    public void setMovieList(List<GetAnswersPojo> a1) {
        this.a1 = a1;
        notifyDataSetChanged();
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.getanswers_child, parent, false);
        return new MyviewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, final int position) {
        holder.tv_answer.setText(a1.get(position).getAnswer());
        holder.tv_answerby.setText("Posted By   :" + a1.get(position).getAnswer_by());
        holder.tv_answer1by.setText("Answer By   :" + a1.get(position).getAnswer_by());
        holder.tv_answer1date.setText("Date   :" + a1.get(position).getAnswer_bydate());


        if(db.isBookMarked(a1.get(position).getAnswer())){
            holder.ivBookMark.setBackgroundResource(R.drawable.ic_book_mark_sel);
        }else{
            holder.ivBookMark.setBackgroundResource(R.drawable.ic_book_mark_unsel);
        }

        holder.ivBookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!db.isBookMarked(a1.get(position).getAnswer())) {
                    holder.ivBookMark.setBackgroundResource(R.drawable.ic_book_mark_sel);
                    long l = db.addQueAns(AllAnswersActivity.question, a1.get(position).getAnswer());
                    Toast.makeText(context, "Answer is saved successfully.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Answer is already saved.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        holder.btn_add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, AddCommentActivity.class);
                intent.putExtra("id",a1.get(position).getId());
                context.startActivity(intent);
            }
        });

        holder.btn_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ViewCommentActivity.class);
                intent.putExtra("id",a1.get(position).getId());
                context.startActivity(intent);

            }
        });
        holder.btn_view_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.ll.getVisibility()==View.VISIBLE){
                    holder.ll.setVisibility(View.GONE);
                }else{
                    sumbitdata(holder.tv_ans1,holder.tv_ans2,holder.tv_ans3,a1.get(position).getId(),holder.btn_view_more);
                    holder.ll.setVisibility(View.VISIBLE);
                }

            }

        });
    }
    @Override
    public int getItemCount() {
        if (a1 != null) {
            return a1.size();
        }
        return 0;

    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView tv_answer,tv_answerby,tv_answer1by,tv_ans1,tv_ans2,tv_ans3,tv_answer1date;
        Button btn_add_comment,btn_view_comment,btn_view_more;
        ImageView ivBookMark;
        LinearLayout ll;
        public MyviewHolder(View itemView) {
            super(itemView);
           ll=(LinearLayout)itemView.findViewById(R.id.ll);
            tv_answer = (TextView) itemView.findViewById(R.id.tv_answer);
            ivBookMark = (ImageView) itemView.findViewById(R.id.iv_BookMark);
            ivBookMark.setVisibility(View.VISIBLE);
            tv_answerby = (TextView) itemView.findViewById(R.id.tv_answerby);
            tv_answer1by = (TextView) itemView.findViewById(R.id.tv_answer1by);
            tv_answer1date=(TextView)itemView.findViewById(R.id.tv_answer1date);
            btn_add_comment = (Button) itemView.findViewById(R.id.btn_add_comment);
            btn_view_comment = (Button) itemView.findViewById(R.id.btn_view_comment);
            btn_view_more= (Button) itemView.findViewById(R.id.btn_view_more);
             tv_ans1=(TextView)itemView.findViewById(R.id.tv_ans1);
             tv_ans2=(TextView)itemView.findViewById(R.id.tv_ans2);
              tv_ans3=(TextView)itemView.findViewById(R.id.tv_ans3);


        }
    }
    List<CommentsPojo> al;
    public void sumbitdata(final TextView tv_ans1,final TextView tv_ans2,final TextView tv_ans3,final String id,final Button btn_view_more){
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<CommentsPojo>> call = apiService.getComments(id);
        call.enqueue(new Callback<List<CommentsPojo>>() {
            @Override
            public void onResponse(Call<List<CommentsPojo>> call, Response<List<CommentsPojo>> response) {
                al = response.body();
                if(a1!=null) {
                    if(al.size() >0){
                        btn_view_more.setVisibility(View.VISIBLE);
                        if(al.size()==1){
                            CommentsPojo user = al.get(0);
                            tv_ans1.setText(user.getComment());
                        }
                        if(al.size()==2){
                            CommentsPojo user1 = al.get(0);
                            tv_ans1.setText(user1.getComment());
                            CommentsPojo user2 = al.get(1);
                            tv_ans2.setText(user2.getComment());
                        }
                        if(al.size()>=3){
                            CommentsPojo user1 = al.get(0);
                            tv_ans1.setText(user1.getComment());
                            CommentsPojo user2 = al.get(1);
                            tv_ans2.setText(user2.getComment());
                            CommentsPojo user3 = al.get(2);
                            tv_ans3.setText(user3.getComment());
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<CommentsPojo>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });
    }


}
