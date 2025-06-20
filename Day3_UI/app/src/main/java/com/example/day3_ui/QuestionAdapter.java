package com.example.day3_ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {


    List<String>  textList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        ImageView arrowImage;


        public ViewHolder(View view) {

            super(view);

            text = (TextView) view.findViewById(R.id.question_text_item);

            arrowImage = (ImageView) view.findViewById(R.id.image_item);

        }


    }

    public QuestionAdapter(List<String> textList ) {

        this.textList = textList;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {

        View view = LayoutInflater
                .from( parent.getContext() )
                .inflate( R.layout.list_question_item, parent, false );

        ViewHolder holder = new ViewHolder(view);

        return holder;


    }


    @Override
    public void onBindViewHolder( ViewHolder holder, int position ) {


        String s = textList.get(position);

        holder.text.setText( s );


    }


    @Override
    public int getItemCount( ){

        return textList.size();

    }








}
