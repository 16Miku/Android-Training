package com.example.day5_search;



import androidx.room.Dao; // 导入 Room Dao 注解
import androidx.room.Insert; // 导入 Room Insert 注解
import androidx.room.OnConflictStrategy; // 导入 Room OnConflictStrategy
import androidx.room.Query; // 导入 Room Query 注解

import com.example.day5_search.GameInfo; // 导入 GameInfo 实体

import java.util.List;

// @Dao 注解表示这是一个Room的DAO接口
@Dao
public interface GameInfoDao {

    // @Insert 注解用于插入数据
    // onConflict = OnConflictStrategy.REPLACE 表示如果发生主键冲突，则替换旧数据
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGameInfo(GameInfo gameInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllGameInfo(List<GameInfo> gameInfos);

    // @Query 注解用于执行自定义SQL查询
    // 查询所有游戏信息
    @Query("SELECT * FROM game_info")
    List<GameInfo> getAllGameInfo();

    // 删除所有游戏信息
    @Query("DELETE FROM game_info")
    void deleteAllGameInfo();

    // 查询数据库中的总记录数
    @Query("SELECT COUNT(*) FROM game_info")
    int getGameInfoCount();

    // 用于功能三：从本地数据库分页获取游戏信息
    @Query("SELECT * FROM game_info LIMIT :limit OFFSET :offset")
    List<GameInfo> getGameInfoPaged(int limit, int offset);
}
