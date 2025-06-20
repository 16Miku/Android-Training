package com.example.day5_search;



import androidx.room.Database; // 导入 Room Database 注解
import androidx.room.RoomDatabase; // 导入 RoomDatabase 基类

import com.example.day5_search.GameInfo; // 导入 GameInfo 实体

// @Database 注解表示这是一个Room数据库
// entities：指定数据库包含的实体类数组
// version：数据库版本号，每次数据库结构变化时需要递增
// exportSchema：是否导出数据库Schema到JSON文件，建议在生产环境中设置为false
@Database(entities = {GameInfo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    // 抽象方法，返回DAO接口的实例。Room会自动生成其实现。
    public abstract GameInfoDao gameInfoDao();
}
