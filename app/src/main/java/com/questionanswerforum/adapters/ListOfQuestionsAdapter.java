package com.questionanswerforum.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.questionanswerforum.Pojo.QuestionsPojo1;
import com.questionanswerforum.activities.AllAnswersActivity;
import com.questionanswerforum.activities.DatabaseHandler;
import com.questionanswerforum.activities.GetAnswersActivity;
import com.questionanswerforum.activities.GiveAnswerActivity;
import com.questionanswerforum.Pojo.QuestionsPojo;
import com.questionanswerforum.R;

import java.util.List;

public class ListOfQuestionsAdapter extends RecyclerView.Adapter<ListOfQuestionsAdapter.MyviewHolder> {

    Context context;
    List<QuestionsPojo1> a1;
    DatabaseHandler db;
    public ListOfQuestionsAdapter(Context context, List<QuestionsPojo1> movieList) {
        this.context = context;
        this.a1 = movieList;
        db=new DatabaseHandler(context);
    }

    public void setMovieList(List<QuestionsPojo1> a1) {
        this.a1 = a1;

        notifyDataSetChanged();
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listofquestions_child,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, final int position) {
        holder.tv_postedby.setText("Asked By  :"+a1.get(position).getPost_by());
       // holder.tv_subject.setText("Subject Title  :"+a1.get(position).getSubject());
        holder.tv_subject.setText(a1.get(position).getQuestion());
        if(db.isBookQueMarked(a1.get(position).getQuestion())) {
            holder.rl.setBackgroundColor(Color.parseColor("#94f2ac"));
        }

        holder.tv_qstn.setText(a1.get(position).getQuestion());
        holder.tv_tags.setText(a1.get(position).getTags());
        holder.tv_created_by.setText("Created Date  :"+a1.get(position).getCdate());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, AllAnswersActivity.class);
                intent.putExtra("id",a1.get(position).getId());
                intent.putExtra("qstn_id",a1.get(position).getId());
                intent.putExtra("sub_name",a1.get(position).getSubject());
                intent.putExtra("qstn",a1.get(position).getQuestion());
                intent.putExtra("tags",a1.get(position).getTags());
                intent.putExtra("post_by",a1.get(position).getPost_by());
                context.startActivity(intent);
            }
        });

        holder.btn_ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, GiveAnswerActivity.class);
                intent.putExtra("qstn_id",a1.get(position).getId());
                intent.putExtra("sub_name",a1.get(position).getSubject());
                intent.putExtra("qstn",a1.get(position).getQuestion());
                intent.putExtra("tags",a1.get(position).getTags());
                intent.putExtra("post_by",a1.get(position).getPost_by());
                context.startActivity(intent);
            }
        });

        holder.btn_view_ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, GetAnswersActivity.class);
                intent.putExtra("id",a1.get(position).getId());
                context.startActivity(intent);
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
        TextView tv_postedby,tv_subject,tv_qstn,tv_tags,tv_created_by;
        Button btn_ans,btn_view_ans;
        CardView cv;
        RelativeLayout rl;
        public MyviewHolder(View itemView) {
            super(itemView);
            btn_ans=(Button)itemView.findViewById(R.id.btn_ans);
            btn_view_ans=(Button)itemView.findViewById(R.id.btn_view_ans);
            tv_postedby = (TextView) itemView.findViewById(R.id.tv_postedby);
            tv_subject = (TextView) itemView.findViewById(R.id.tv_subject);
            tv_qstn = (TextView) itemView.findViewById(R.id.tv_qstn);
            tv_tags = (TextView) itemView.findViewById(R.id.tv_tags);
            tv_created_by = (TextView) itemView.findViewById(R.id.tv_created_by);
            cv= (CardView) itemView.findViewById(R.id.cv);
            rl=(RelativeLayout)itemView.findViewById(R.id.rl);

        }
    }
}