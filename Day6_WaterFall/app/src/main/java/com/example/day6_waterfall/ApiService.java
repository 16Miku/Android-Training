package com.example.day6_waterfall;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Retrofit API 接口定义
 * 用于从 picsum.photos 获取图片 URL
 */
public interface ApiService {
    /**
     * 获取图片地址
     * 示例：https://picsum.photos/400/300
     * 注意：这个API会返回一个 HTTP 302 重定向，重定向的 URL 才是最终的图片 URL，
     * 包含唯一的 ID 和 HMAC，确保每次获取的图片是新的。
     *
     * @param width 图片宽度（固定400）
     * @param height 图片高度（随机200~800）
     * @return Call<Void> 因为我们不关心响应体，只关心重定向的 URL
     */
    @GET("{width}/{height}")
    Call<Void> getPhotoUrl(
            @Path("width") int width,
            @Path("height") int height
    );
}

