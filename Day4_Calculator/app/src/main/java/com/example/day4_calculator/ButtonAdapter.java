package com.example.day4_calculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {


    List<String> buttonLabels;

    OnButtonClickListener listener;

    public interface OnButtonClickListener {

        void onButtonClick( String label );

    }

    public ButtonAdapter( List<String> buttonLabels, OnButtonClickListener listener ) {

        this.buttonLabels = buttonLabels;

        this.listener = listener;
    }


    static class ButtonViewHolder extends RecyclerView.ViewHolder {

        Button button;

        ButtonViewHolder(@NonNull View itemview) {

            super(itemview);


            button = itemview.findViewById(R.id.item_button);


        }
    }



    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ) {

        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_button, parent, false );

        return new ButtonViewHolder(view);

    }


    @Override
    public void onBindViewHolder( @NonNull ButtonViewHolder holder, int position ) {


        String label = buttonLabels.get( position );

        holder.button.setText( label );

        // 设置按钮点击事件
        holder.button.setOnClickListener(v -> {
            if (listener != null) {
                listener.onButtonClick(label); // 调用回调接口通知 MainActivity
            }
        });

    }

    @Override
    public int getItemCount() {

        return buttonLabels.size();
    }







}
