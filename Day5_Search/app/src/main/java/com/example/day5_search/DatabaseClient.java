package com.example.day5_search;



import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private static final String DATABASE_NAME = "game_database"; // 数据库文件名
    private static DatabaseClient instance;
    private AppDatabase appDatabase;

    // 私有构造函数，确保只能通过getInstance()获取实例
    private DatabaseClient(Context context) {
        // 构建Room数据库实例
        // context.getApplicationContext() 确保使用Application级别的Context，避免内存泄漏
        appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATABASE_NAME)
                // .allowMainThreadQueries() // 警告：仅用于演示和测试，生产环境强烈不推荐！会阻塞UI线程。
                // 生产环境应确保所有数据库操作都在后台线程执行（例如使用ExecutorService或协程）
                .build();
    }

    /**
     * 获取DatabaseClient的单例实例
     * @param context 应用上下文
     * @return DatabaseClient实例
     */
    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    /**
     * 获取AppDatabase实例
     * @return AppDatabase实例
     */
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}

