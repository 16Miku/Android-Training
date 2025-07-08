package com.example.okhttptest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {



    private static final String TAG = "OkHttpExample";
    private TextView resultTextView;
    private Button syncGetButton, asyncGetButton;

    // 推荐使用单例模式的 OkHttpClient
    private static OkHttpClient client;

    //  创建一个Handler实例，它默认与当前线程（主线程）的Looper关联
    private final Handler mainHandler = new Handler(Looper.getMainLooper()); // 用于更新UI




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);
        syncGetButton = findViewById(R.id.syncGetButton);
        asyncGetButton = findViewById(R.id.asyncGetButton);

        // 初始化 OkHttpClient (单例)
        if (client == null) {
            client = new OkHttpClient();
        }

        // 异步 GET 请求按钮点击事件
        asyncGetButton.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fetchDataAsync( "https://www.upc.edu.cn/" );
                    }
                }

        );



    }


    /**
     * 执行异步 GET 请求
     */
    public void fetchDataAsync(String url ) {

        // 1. 构建 Request 对象
        Request request = new Request.Builder()
                .url( url ) // 设置请求URL
                .get()      // 设置请求方法为 GET (默认就是GET，可以省略)
                .build();

        // 2. 通过 OkHttpClient 创建 Call 对象
        Call call = client.newCall(request);

        // 3. 执行异步请求，并提供 Callback
        call.enqueue(

                new Callback() {

                    // 请求失败时调用
                    @Override
                    public void onFailure(@NonNull Call call, IOException e) {

                        Log.e(TAG, "Async GET Failed: " + e.getMessage());

                        // 消息发送： 任何线程都可以通过 Handler 的 sendMessage() 或 post() 系列方法，将 Message 或 Runnable 发送到与该 Handler 绑定的 Looper 的 MessageQueue 中。
                        mainHandler.post(

                                () -> {

                                    resultTextView.setText( "Async GET Failed:\n" + e.getMessage() );

                                    Toast.makeText(MainActivity.this, "异步请求GET失败", Toast.LENGTH_SHORT ).show();


                                }



                        );

                        e.printStackTrace();


                    }

                    // 请求成功并收到响应时调用
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        // 注意：此回调在后台线程执行，不能直接更新UI
                        try (ResponseBody responseBody = response.body()) {
                            // 使用 try-with-resources 确保 ResponseBody 关闭

                            if (response.isSuccessful()) {

                                if (responseBody != null) {


                                    String responseData = responseBody.string();

                                    Log.d(TAG, "Async GET Response: " + responseData);


                                    // 在主线程更新UI
                                    // 消息发送： 任何线程都可以通过 Handler 的 sendMessage() 或 post() 系列方法，将 Message 或 Runnable 发送到与该 Handler 绑定的 Looper 的 MessageQueue 中。
                                    mainHandler.post(


                                            () -> {

                                                resultTextView.setText("Async GET Success:\n" + responseData);

                                                Toast.makeText(MainActivity.this, "异步GET请求成功", Toast.LENGTH_SHORT).show();
                                            }
                                    );


                                }
                            } else {

                                Log.e(TAG, "Async GET Failed: " + response.code() + " " + response.message());

                            }

                        }


                    }

                }


        );


    }

}