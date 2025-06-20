package com.example.day5_search; // 确保包名正确

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView; // 导入ImageView
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide; // 导入Glide
import com.example.day5_search.R;
import com.example.day5_search.GameInfo; // 确保导入正确的GameInfo类

import java.util.ArrayList;
import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameViewHolder> {

    private List<GameInfo> gameList = new ArrayList<>();

    public void setData(List<GameInfo> newGameList) {
        this.gameList.clear();
        if (newGameList != null) {
            this.gameList.addAll(newGameList);
        }
        notifyDataSetChanged();
    }

    public void appendData(List<GameInfo> newGameList) {
        if (newGameList != null && !newGameList.isEmpty()) {
            int startPosition = gameList.size();
            this.gameList.addAll(newGameList);
            notifyItemRangeInserted(startPosition, newGameList.size());
        }
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_info, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        GameInfo game = gameList.get(position);
        holder.tvGameName.setText("游戏名称: " + game.getGameName());
        holder.tvGameId.setText("游戏ID: " + game.getGameId());
        holder.tvGameDescription.setText("描述: " + game.getGameDescription());

        // 添加这行日志
        Log.d("GlideDebug", "Loading image for " + game.getGameName() + ": " + game.getGameIconUrl());



        // 使用Glide加载游戏图标
        if (game.getGameIconUrl() != null && !game.getGameIconUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext()) // 使用itemView的Context
                    .load(game.getGameIconUrl()) // 加载图片URL
                    .placeholder(R.drawable.ic_launcher_foreground) // 加载中显示的占位图
                    .error(R.drawable.ic_launcher_background) // 加载失败显示的错误图
                    .into(holder.ivGameIcon); // 将图片加载到ImageView
        } else {
            // 如果没有图标URL，显示默认占位图或清空ImageView
            holder.ivGameIcon.setImageResource(R.drawable.ic_launcher_foreground);
        }
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView tvGameName;
        TextView tvGameId;
        TextView tvGameDescription;
        ImageView ivGameIcon; // 声明ImageView

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGameName = itemView.findViewById(R.id.tv_game_name);
            tvGameId = itemView.findViewById(R.id.tv_game_id);
            tvGameDescription = itemView.findViewById(R.id.tv_game_description);
            ivGameIcon = itemView.findViewById(R.id.iv_game_icon); // 查找ImageView
        }
    }
}
