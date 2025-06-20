package com.example.day5_search;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

// 确保导入了所有必要的类，特别是您自定义的POJO和Room相关类
// 注意：根据您提供的文件，您的POJO和Room相关类都在com.example.day5_search包下
// 如果您之前按照建议将它们移动到了model和database子包，请调整这里的import语句
// import com.example.day5_search.adapter.GameListAdapter;
// import com.example.day5_search.database.AppDatabase;
// import com.example.day5_search.database.DatabaseClient;
// import com.example.day5_search.database.GameInfoDao;
// import com.example.day5_search.model.BaseResponse;
// import com.example.day5_search.model.GameInfo;
// import com.example.day5_search.model.GameInfoPage;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "OkHttpExample";
    private Button syncGetButton, asyncGetButton;
    private RecyclerView recyclerView;
    private GameListAdapter gameListAdapter;
    private EditText searchText;

    private static OkHttpClient client;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final Gson gson = new Gson();

    private final java.lang.reflect.Type gameInfoPageResponseType = new TypeToken<BaseResponse<GameInfoPage>>() {}.getType();

    // Room 数据库相关
    private GameInfoDao gameInfoDao;
    private ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();

    // 用于网络请求分页加载的变量
    private int currentPage = 1;
    private final int pageSize = 10; // 每页10条数据
    private boolean isLoading = false; // 标记是否正在加载网络数据
    private boolean isLastPage = false; // 标记网络数据是否是最后一页
    private String currentSearchTerm = ""; // 保存当前搜索词

    // 用于本地数据展示的变量 (功能三)
    private final int LOCAL_DISPLAY_PAGE_SIZE = 5; // 每次展示5条数据
    private int localDisplayCurrentOffset = 0; // 当前本地数据展示的起始偏移量
    private List<GameInfo> allLocalGames = new ArrayList<>(); // 存储从本地数据库加载的所有游戏数据
    private Runnable refreshLocalDataRunnable; // 定时刷新任务
    private static final long REFRESH_INTERVAL_MS = 5000; // 5秒刷新一次


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        searchText = findViewById(R.id.searchText);
        syncGetButton = findViewById(R.id.syncGetButton);
        asyncGetButton = findViewById(R.id.asyncGetButton);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gameListAdapter = new GameListAdapter();
        recyclerView.setAdapter(gameListAdapter);

        if (client == null) {
            client = new OkHttpClient();
        }

        // 初始化 Room 数据库 DAO
        gameInfoDao = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().gameInfoDao();

        // 异步 GET 请求按钮点击事件
        asyncGetButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentSearchTerm = String.valueOf(searchText.getEditableText()).trim();
                        if (TextUtils.isEmpty(currentSearchTerm)) {
                            Toast.makeText(MainActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        currentPage = 1;
                        isLastPage = false;
                        fetchDataAsync(currentSearchTerm, currentPage, pageSize, true);
                    }
                }
        );

        // 添加上拉加载更多监听器 (此监听器用于网络请求的分页加载)
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    // 判断是否滑动到底部，并且当前没有在加载中，也不是最后一页
                    // 注意：这里的totalItemCount是当前RecyclerView显示的数量，而不是数据库总数
                    // 只有当网络请求返回新数据时，才触发加载更多
                    if (!isLoading && !isLastPage &&
                            (visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                            firstVisibleItemPosition >= 0) {

                        Log.d(TAG, "Network: Loading more data...");
                        currentPage++;
                        fetchDataAsync(currentSearchTerm, currentPage, pageSize, false);
                    }
                }
            }
        });

        // 在Activity创建时，立即加载并显示本地数据，并启动定时刷新
        loadAndDisplayLocalData(); // 首次加载本地数据
        startLocalDataRefresh(); // 启动定时刷新
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 当Activity回到前台时，确保定时刷新任务是运行的
        startLocalDataRefresh();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 当Activity进入后台时，停止定时刷新任务，避免资源浪费
        stopLocalDataRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭ExecutorService，释放资源
        if (databaseExecutor != null && !databaseExecutor.isShutdown()) {
            databaseExecutor.shutdown();
        }
        // 确保在Activity销毁时停止所有Handler回调，防止内存泄漏
        stopLocalDataRefresh();
    }

    /**
     * 执行异步 GET 请求
     * @param searchTerm 搜索关键词
     * @param page 当前页码
     * @param intSize 每页大小
     * @param isNewSearch 是否是新的搜索或刷新操作 (true表示清空旧数据，false表示追加数据)
     */
    public void fetchDataAsync(String searchTerm, int page, int intSize, boolean isNewSearch) {
        if (isLoading) {
            Log.d(TAG, "Already loading, skipping new network request.");
            return;
        }
        isLoading = true; // 标记网络请求正在进行

        String encodedSearchTerm;
        try {
            encodedSearchTerm = URLEncoder.encode(searchTerm, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            Log.e(TAG, "UTF-8 encoding not supported", e);
            mainHandler.post(() -> Toast.makeText(MainActivity.this, "编码错误", Toast.LENGTH_SHORT).show());
            isLoading = false;
            return;
        }

        String url = "https://hotfix-service-prod.g.mi.com/quick-game/game/search?" +
                "search=" + encodedSearchTerm +
                "&current=" + page +
                "&size=" + intSize;

        Log.d(TAG, "Network Request URL: " + url);

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Accept", "application/json")
                .build();

        Call call = client.newCall(request);

        call.enqueue(
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, IOException e) {
                        Log.e(TAG, "Async GET Failed: " + e.getMessage());
                        mainHandler.post(
                                () -> {
                                    Toast.makeText(MainActivity.this, "网络请求失败: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                        );
                        isLoading = false; // 网络请求结束
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        try (ResponseBody responseBody = response.body()) {
                            if (response.isSuccessful()) {
                                if (responseBody != null) {
                                    String responseData = responseBody.string();
                                    Log.d(TAG, "Async GET Response: " + responseData);

                                    BaseResponse<GameInfoPage> baseResponse = gson.fromJson(responseData, gameInfoPageResponseType);

                                    if (baseResponse != null && baseResponse.getCode() == 200 && baseResponse.getData() != null) {
                                        GameInfoPage gameInfoPage = baseResponse.getData();
                                        List<GameInfo> gameInfos = gameInfoPage.getRecords();

                                        // 将数据保存到本地数据库
                                        saveGamesToLocal(gameInfos, isNewSearch); // 调用保存方法

                                        mainHandler.post(
                                                () -> {
                                                    if (gameInfos != null && !gameInfos.isEmpty()) {
                                                        Toast.makeText(MainActivity.this, "网络请求成功，数据已保存到本地", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        if (isNewSearch) {
                                                            // 如果是新搜索但没有数据，也清空本地
                                                            saveGamesToLocal(new ArrayList<>(), true);
                                                            Toast.makeText(MainActivity.this, "未找到匹配数据，本地已清空", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(MainActivity.this, "网络没有更多数据了", Toast.LENGTH_SHORT).show();
                                                            isLastPage = true; // 标记网络数据为最后一页
                                                        }
                                                    }
                                                    // 无论网络请求结果如何，都触发本地数据刷新显示
                                                    loadAndDisplayLocalData();
                                                }
                                        );

                                        // 判断网络请求是否是最后一页
                                        if (gameInfoPage.getCurrent() >= gameInfoPage.getPages()) {
                                            isLastPage = true;
                                            Log.d(TAG, "Network: Reached last page.");
                                        } else {
                                            isLastPage = false;
                                        }

                                    } else {
                                        String errorMsg = baseResponse != null ? baseResponse.getMsg() : "未知业务错误";
                                        Log.e(TAG, "API Business Error: " + errorMsg);
                                        mainHandler.post(() -> Toast.makeText(MainActivity.this, "API业务错误: " + errorMsg, Toast.LENGTH_LONG).show());
                                    }
                                } else {
                                    Log.e(TAG, "Response body is null.");
                                    mainHandler.post(() -> Toast.makeText(MainActivity.this, "响应体为空", Toast.LENGTH_SHORT).show());
                                }
                            } else {
                                String errorBody = response.body() != null ? response.body().string() : "No error body";
                                Log.e(TAG, "Async GET Failed (HTTP " + response.code() + "): " + response.message() + ", Body: " + errorBody);
                                mainHandler.post(() -> {
                                    Toast.makeText(MainActivity.this, "网络请求失败: " + response.code(), Toast.LENGTH_LONG).show();
                                });
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "JSON parsing error: " + e.getMessage(), e);
                            mainHandler.post(() -> Toast.makeText(MainActivity.this, "数据解析失败: " + e.getMessage(), Toast.LENGTH_LONG).show());
                        } finally {
                            isLoading = false; // 网络请求结束
                        }
                    }
                }
        );
    }

    /**
     * 将游戏信息保存到本地数据库
     * @param gameInfos 游戏信息列表
     * @param clearExisting 是否清空现有数据 (如果是新搜索/刷新，则清空)
     */
    private void saveGamesToLocal(List<GameInfo> gameInfos, boolean clearExisting) {
        databaseExecutor.execute(() -> { // 在后台线程执行数据库操作
            if (clearExisting) {
                gameInfoDao.deleteAllGameInfo();
                Log.d(TAG, "Cleared all existing game info from local database.");
            }
            if (gameInfos != null && !gameInfos.isEmpty()) {
                gameInfoDao.insertAllGameInfo(gameInfos);
                Log.d(TAG, "Saved " + gameInfos.size() + " games to local database.");
            } else {
                Log.d(TAG, "No games to save or list is empty.");
            }
            // 保存完成后，立即触发本地数据刷新显示
            mainHandler.post(() -> loadAndDisplayLocalData());
        });
    }

    /**
     * 从本地数据库加载数据并更新RecyclerView
     * 这个方法会在主线程调用，但实际的数据库查询会在后台线程执行
     */
    private void loadAndDisplayLocalData() {
        databaseExecutor.execute(() -> { // 在后台线程执行数据库查询
            // 1. 获取本地数据库中的所有游戏数据
            allLocalGames = gameInfoDao.getAllGameInfo();
            Log.d(TAG, "Local DB: Total games loaded: " + allLocalGames.size());

            // 2. 计算当前要展示的5条数据
            List<GameInfo> gamesToDisplay = new ArrayList<>();
            if (!allLocalGames.isEmpty()) {
                // 确保偏移量在有效范围内
                if (localDisplayCurrentOffset >= allLocalGames.size()) {
                    localDisplayCurrentOffset = 0; // 如果超出范围，则从头开始
                }

                int endIndex = Math.min(localDisplayCurrentOffset + LOCAL_DISPLAY_PAGE_SIZE, allLocalGames.size());
                gamesToDisplay = allLocalGames.subList(localDisplayCurrentOffset, endIndex);

                // 更新下一个偏移量
                localDisplayCurrentOffset += LOCAL_DISPLAY_PAGE_SIZE;
            }

            // 3. 在主线程更新RecyclerView
            List<GameInfo> finalGamesToDisplay = gamesToDisplay; // 局部变量需要final或effectively final
            mainHandler.post(() -> {
                if (finalGamesToDisplay.isEmpty()) {
                    gameListAdapter.setData(new ArrayList<>()); // 如果没有数据，清空列表
                    Toast.makeText(MainActivity.this, "本地无数据可显示", Toast.LENGTH_SHORT).show();
                } else {
                    gameListAdapter.setData(finalGamesToDisplay); // 更新适配器数据
                    Toast.makeText(MainActivity.this, "本地数据刷新，显示 " + finalGamesToDisplay.size() + " 条", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    /**
     * 启动本地数据定时刷新任务
     */
    private void startLocalDataRefresh() {
        if (refreshLocalDataRunnable == null) {
            refreshLocalDataRunnable = new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Triggering local data refresh...");
                    loadAndDisplayLocalData(); // 加载并显示本地数据
                    mainHandler.postDelayed(this, REFRESH_INTERVAL_MS); // 再次调度自己
                }
            };
        }
        // 移除任何待处理的回调，防止重复调度
        mainHandler.removeCallbacks(refreshLocalDataRunnable);
        // 首次调度
        mainHandler.postDelayed(refreshLocalDataRunnable, REFRESH_INTERVAL_MS);
        Log.d(TAG, "Local data refresh started.");
    }

    /**
     * 停止本地数据定时刷新任务
     */
    private void stopLocalDataRefresh() {
        if (refreshLocalDataRunnable != null) {
            mainHandler.removeCallbacks(refreshLocalDataRunnable);
            Log.d(TAG, "Local data refresh stopped.");
        }
    }
}
