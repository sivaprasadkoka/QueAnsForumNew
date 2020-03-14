package com.questionanswerforum.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.questionanswerforum.Pojo.MyAnswersPojo;
import com.questionanswerforum.Pojo.QueAnsPojo;
import com.questionanswerforum.R;
import com.questionanswerforum.activities.GetAnswersActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookmarkAdapter extends BaseAdapter {
    List<QueAnsPojo> lqa;
    Context cnt;
    public BookmarkAdapter(List<QueAnsPojo> ar, Context cnt)
    {
        this.lqa=ar;
        this.cnt=cnt;

    }
    @Override
    public int getCount() {
        return lqa.size();
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
        View obj2=obj1.inflate(R.layout.row_bookmark_que_ans,null);
        TextView tv_question=(TextView) obj2.findViewById(R.id.tv_question);
        tv_question.setText("Que :"+lqa.get(position).getQuestion());
        TextView tv_answer=(TextView) obj2.findViewById(R.id.tv_answer);
        tv_answer.setText("Ans :"+lqa.get(position).getAnswer());

        return obj2;
    }

}