package com.example.day6_waterfall;



import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory; // 即使不解析JSON，也通常会添加

/**
 * Retrofit 客户端单例
 * 用于创建和管理 Retrofit 实例及 ApiService 实例
 */
public class RetrofitClient {
    // 基础 URL
    private static final String BASE_URL = "https://picsum.photos/";
    // 单例对象
    private static volatile Retrofit retrofit;

    // 私有构造方法（防止外部实例化）
    private RetrofitClient() {}

    /**
     * 获取 Retrofit 实例
     * 使用双重检查锁定（Double-Checked Locking）确保线程安全和高性能
     * @return Retrofit 实例
     */
    public static Retrofit getInstance() {
        if (retrofit == null) {
            synchronized (RetrofitClient.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            // 虽然我们不解析 JSON 响应体，但添加转换器是常见做法，
                            // 也可以不加，因为我们只关心重定向的 URL
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    /**
     * 获取 ApiService 实例
     * @return ApiService 实例
     */
    public static ApiService getApiService() {
        return getInstance().create(ApiService.class);
    }
}

