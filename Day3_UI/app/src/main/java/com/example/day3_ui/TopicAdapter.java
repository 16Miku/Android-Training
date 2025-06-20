package com.example.day3_ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {


    List<String>  textList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;



        public ViewHolder(View view) {

            super(view);

            text = (TextView) view.findViewById(R.id.topic_text_item);


        }


    }

    public TopicAdapter(List<String> textList ) {

        this.textList = textList;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {

        View view = LayoutInflater
                .from( parent.getContext() )
                .inflate( R.layout.list_topic_item, parent, false );

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
